package com.iprody08.customerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iprody08.customerservice.dto.CustomerDto;
import com.iprody08.customerservice.services.CustomerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static com.iprody08.customerservice.controller.TestConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class CustomerControllerIT {

    @Autowired
    private CustomerService customerService;

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddCustomer() throws Exception {
        // given
        CustomerDto customerDto = CustomerDto.builder()
                .name(USERNAME_FIRST)
                .surname(SURNAME_FIRST)
                .telegramId(TELEGRAM_ID_FIRST_IT)
                .email(EMAIL_FIRST_IT)
                .countryId(COUNTRY_ID_FIRST)
                .build();

        // when
        mockMvc.perform(post(BASE_URL)
                        .content(objectMapper.writeValueAsString(customerDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(USERNAME_FIRST))
                .andExpect(jsonPath("$.surname").value(SURNAME_FIRST));

        Optional<CustomerDto> testCustomer = customerService.findCustomerById(CUSTOMER_ID_FIRST);
        assertTrue(testCustomer.isPresent(), "Customer add to db success");
        assertEquals(USERNAME_FIRST, testCustomer.get().getName(), "Customer name success");
        assertEquals(SURNAME_FIRST, testCustomer.get().getSurname(), "Customer surname success");
    }

    @Test
    public void testGetCustomerById() throws Exception {
        // given
        CustomerDto customer = addCustomer();
        Long customerId = customer.getId();

        // when
        MvcResult mvcResult = mockMvc.perform(get(GET_CUSTOMER_BY_ID, customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.name").value(USERNAME_FIRST))
                .andExpect(jsonPath("$.surname").value(SURNAME_FIRST))
                .andReturn();

        CustomerDto getCustomer = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(), CustomerDto.class);

        assertNotNull(getCustomer);
        assertEquals(customer.getId(), getCustomer.getId(), "Customer ID success");
        assertEquals(customer.getName(), getCustomer.getName(), "Customer name success");
        assertEquals(customer.getSurname(), getCustomer.getSurname(), "Customer surname success");
    }

    private CustomerDto addCustomer() {
        CustomerDto customerDto = CustomerDto.builder()
                .name(USERNAME_FIRST)
                .surname(SURNAME_FIRST)
                .telegramId(TELEGRAM_ID_FIRST_IT)
                .email(EMAIL_FIRST_IT)
                .countryId(COUNTRY_ID_FIRST)
                .build();

        return customerService.addCustomer(customerDto);
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        // given
        CustomerDto customer1 = CustomerDto.builder()
                .name(USERNAME_FIRST)
                .surname(SURNAME_FIRST)
                .email(EMAIL_FIRST_IT)
                .telegramId(TELEGRAM_ID_FIRST_IT)
                .countryId(COUNTRY_ID_FIRST)
                .build();
        CustomerDto customer2 = CustomerDto.builder()
                .name(USERNAME_SECOND)
                .surname(SURNAME_SECOND)
                .email(EMAIL_SECOND_IT)
                .telegramId(TELEGRAM_ID_SECOND_IT)
                .countryId(COUNTRY_ID_SECOND)
                .build();

        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);

        // when
        mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(USERNAME_FIRST))
                .andExpect(jsonPath("$[0].surname").value(SURNAME_FIRST))
                .andExpect(jsonPath("$[1].name").value(USERNAME_SECOND))
                .andExpect(jsonPath("$[1].surname").value(SURNAME_SECOND));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        // given
        CustomerDto originalCustomer = CustomerDto.builder()
                .name(USERNAME_FIRST)
                .surname(SURNAME_FIRST)
                .telegramId(TELEGRAM_ID_FIRST_IT)
                .email(EMAIL_FIRST_IT)
                .countryId(COUNTRY_ID_FIRST)
                .build();

        CustomerDto addCustomerDB = customerService.addCustomer(originalCustomer);

        addCustomerDB.setName(USERNAME_SECOND);
        addCustomerDB.setSurname(SURNAME_SECOND);

        // when
        mockMvc.perform(put(GET_CUSTOMER_BY_ID, addCustomerDB.getId())
                        .content(objectMapper.writeValueAsString(addCustomerDB))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(USERNAME_SECOND))
                .andExpect(jsonPath("$.surname").value(SURNAME_SECOND));

        Optional<CustomerDto> getCustomer = customerService.findCustomerById(addCustomerDB.getId());
        assertTrue(getCustomer.isPresent(), "Update customer success");
        CustomerDto updateCustomer = getCustomer.get();
        assertEquals(USERNAME_SECOND, updateCustomer.getName(), "Customer name success");
        assertEquals(SURNAME_SECOND, updateCustomer.getSurname(), "Customer surname success");
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        // given
        CustomerDto customerDto = CustomerDto.builder()
                .name(USERNAME_FIRST)
                .surname(SURNAME_FIRST)
                .telegramId(TELEGRAM_ID_FIRST_IT)
                .email(EMAIL_FIRST_IT)
                .countryId(COUNTRY_ID_FIRST)
                .build();

        CustomerDto customer = customerService.addCustomer(customerDto);

        // when
        mockMvc.perform(delete(GET_CUSTOMER_BY_ID, customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isNoContent());

        Optional<CustomerDto> deleteCustomerDB = customerService.findCustomerById(customer.getId());
        assertFalse(deleteCustomerDB.isPresent(), "Customer deleted sucess");
    }
}
