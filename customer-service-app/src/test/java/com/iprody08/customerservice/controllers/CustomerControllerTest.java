package com.iprody08.customerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody08.customerservice.dto.CustomerDto;
import com.iprody08.customerservice.entities.ContactDetails;
import com.iprody08.customerservice.entities.Country;
import com.iprody08.customerservice.entities.Customer;
import com.iprody08.customerservice.mapper.CustomerMapper;
import com.iprody08.customerservice.repositories.ContactDetailsRepository;
import com.iprody08.customerservice.repositories.CountryRepository;
import com.iprody08.customerservice.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CustomerControllerTest {

    private static final String BASE_URL = "/api/customers/";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        JdbcTestUtils.deleteFromTables(
                jdbcTemplate,
                "customers",
                "contact_details");
    }

    @Test
    void getCustomerByTelegramId() throws Exception {
        //given
        CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);
        String telegramId = "@iprody08";
        Country country = countryRepository.findByCountryCode("RUS").get();

        ContactDetails contactDetails = ContactDetails
                .builder()
                .email("iprody08@gmail.com")
                .telegramId(telegramId)
                .build();

        Customer customer = Customer.builder()
                .name("Ivan")
                .surname("Ivanov")
                .contactDetails(contactDetails)
                .country(country)
                .build();
        customerRepository.save(customer);
        CustomerDto actual = mapper.customerToDto(customer);

        // when

        MvcResult result = mvc.perform(get(BASE_URL + "telegram/{id}", telegramId))

        //then

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        CustomerDto expected = objectMapper.readValue(response, CustomerDto.class);

        assertThat(expected).isNotNull();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getSurname(), actual.getSurname());
        assertEquals(expected.getCountryId(), actual.getCountryId());
        assertEquals(expected.getCountryName(), actual.getCountryName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getTelegramId(), actual.getTelegramId());

    }

    @Test
    void getNotFoundCustomerByTelegramId() throws Exception {
        //given
        CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);
        String telegramId = "@iprody08";
        String notFoundTelegramId = "@iprody09";
        Country country = countryRepository.findByCountryCode("RUS").get();

        ContactDetails contactDetails = ContactDetails
                .builder()
                .email("iprody08@gmail.com")
                .telegramId(telegramId)
                .build();

        Customer customer = Customer.builder()
                .name("Ivan")
                .surname("Ivanov")
                .contactDetails(contactDetails)
                .country(country)
                .build();
        customerRepository.save(customer);
        CustomerDto actual = mapper.customerToDto(customer);

        // when

       mvc.perform(get(BASE_URL + "telegram/{id}", notFoundTelegramId))

       //then

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}
