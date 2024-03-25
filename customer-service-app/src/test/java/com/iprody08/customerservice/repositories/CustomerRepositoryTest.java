package com.iprody08.customerservice.repositories;

import com.iprody08.customerservice.entities.ContactDetails;
import com.iprody08.customerservice.entities.Country;
import com.iprody08.customerservice.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    @Test
    public void findCustomerById() {

        //given

        Country country = countryRepository.findById((long) 1).get();

        ContactDetails contactDetails = ContactDetails.builder()
                .email("iprody08@gmail.com")
                .telegramId("@iprody08")
                .build();

        contactDetailsRepository.save(contactDetails);

        Customer customer = Customer.builder()
                .name("Ivan")
                .surname("Ivanov")
                .contactDetails(contactDetails)
                .country(country)
                .build();

        customerRepository.save(customer);

        //when

        Optional<Customer> foundCustomer = customerRepository.findById(customer.getId());

        //then

        assertTrue(foundCustomer.isPresent());
        assertThat(customer).isEqualTo(foundCustomer.get());

    }


}
