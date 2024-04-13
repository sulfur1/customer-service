package com.iprody08.customerservice.controller;

import com.iprody08.customerservice.dto.ContactDetailsDto;
import com.iprody08.customerservice.dto.CustomerDto;
import com.iprody08.customerservice.services.CustomerService;
import com.iprody08.customerservice.util.JsonUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static com.iprody08.customerservice.controller.TestConstant.*;
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

    @Test
    public void testAddCustomer() throws Exception {
        // given
        CustomerDto customerDto = CustomerDto.builder()
                .name(USERNAME_FIRST)
                .surname(SURNAME_FIRST)
                .telegramId(TELEGRAM_ID_FIRST)
                .email(EMAIL_FIRST)
                .countryId(COUNTRY_ID_FIRST)
                .contactDetails(ContactDetailsDto.builder()
                        .email(EMAIL_FIRST)
                        .build())
                .build();

        // when
        mockMvc.perform(post(BASE_URL)
                        .content(JsonUtil.toJson(customerDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(USERNAME_FIRST))
                .andExpect(jsonPath("$.surname").value(SURNAME_FIRST));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        // given
        CustomerDto customer = addCustomer();
        Long customerId = customer.getId();

        // when
        mockMvc.perform(get(GET_CUSTOMER_BY_ID, customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId))
                .andExpect(jsonPath("$.name").value(customer.getName()))
                .andExpect(jsonPath("$.surname").value(customer.getSurname()));
    }

    private CustomerDto addCustomer() {
        CustomerDto customerDto = CustomerDto.builder()
                .name(USERNAME_FIRST)
                .surname(SURNAME_FIRST)
                .telegramId(TELEGRAM_ID_FIRST)
                .email(EMAIL_FIRST)
                .countryId(COUNTRY_ID_FIRST)
                .contactDetails(ContactDetailsDto.builder()
                        .email(EMAIL_FIRST)
                        .build())
                .build();

        return customerService.addCustomer(customerDto);
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        // given
        CustomerDto customer1 = CustomerDto.builder()
                .name(USERNAME_FIRST)
                .surname(SURNAME_FIRST)
                .email(EMAIL_FIRST)
                .telegramId(TELEGRAM_ID_FIRST)
                .countryId(COUNTRY_ID_FIRST)
                .contactDetails(ContactDetailsDto.builder()
                        .email(EMAIL_FIRST)
                        .build())
                .build();
        CustomerDto customer2 = CustomerDto.builder()
                .name(USERNAME_SECOND)
                .surname(SURNAME_SECOND)
                .email(EMAIL_SECOND)
                .telegramId(TELEGRAM_ID_SECOND)
                .countryId(COUNTRY_ID_SECOND)
                .contactDetails(ContactDetailsDto.builder()
                        .email(EMAIL_SECOND)
                        .build())
                .build();

        customerService.addCustomer(customer1);
        customerService.addCustomer(customer2);

        // when
        mockMvc.perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.content[0].name").value(USERNAME_FIRST))
                .andExpect(jsonPath("$.content[0].surname").value(SURNAME_FIRST))
                .andExpect(jsonPath("$.content[1].name").value(USERNAME_SECOND))
                .andExpect(jsonPath("$.content[1].surname").value(SURNAME_SECOND));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        // given
        CustomerDto originalCustomer = CustomerDto.builder()
                .name(USERNAME_FIRST)
                .surname(SURNAME_FIRST)
                .telegramId(TELEGRAM_ID_FIRST)
                .email(EMAIL_FIRST)
                .countryId(COUNTRY_ID_FIRST)
                .contactDetails(ContactDetailsDto.builder()
                        .email(EMAIL_FIRST)
                        .build())
                .build();

        CustomerDto addedCustomer = customerService.addCustomer(originalCustomer);

        addedCustomer.setName(USERNAME_SECOND);
        addedCustomer.setSurname(SURNAME_SECOND);

        // when
        mockMvc.perform(put(GET_CUSTOMER_BY_ID, addedCustomer.getId())
                        .content(JsonUtil.toJson(addedCustomer))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(USERNAME_SECOND))
                .andExpect(jsonPath("$.surname").value(SURNAME_SECOND));
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        // given
        CustomerDto customerDto = CustomerDto.builder()
                .name(USERNAME_FIRST)
                .surname(SURNAME_FIRST)
                .telegramId(TELEGRAM_ID_FIRST)
                .email(EMAIL_FIRST)
                .countryId(COUNTRY_ID_FIRST)
                .contactDetails(ContactDetailsDto.builder()
                        .email(EMAIL_FIRST)
                        .build())
                .build();

        CustomerDto addedCustomer = customerService.addCustomer(customerDto);

        // when
        mockMvc.perform(delete(GET_CUSTOMER_BY_ID, addedCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isNoContent());
    }
}
