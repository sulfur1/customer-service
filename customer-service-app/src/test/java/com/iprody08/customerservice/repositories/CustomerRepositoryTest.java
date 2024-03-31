package com.iprody08.customerservice.repositories;

import com.iprody08.customerservice.entities.ContactDetails;
import com.iprody08.customerservice.entities.Country;
import com.iprody08.customerservice.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void testFindAllCustomers() {

        //given

        Country country = countryRepository.findByCountryCode("RUS").get();

        ContactDetails contactDetails1 = ContactDetails.builder()
                .email("iprody08@gmail.com")
                .telegramId("@iprody08")
                .build();
        ContactDetails contactDetails2 = ContactDetails.builder()
                .email("iprody008@gmail.com")
                .telegramId("@iprody008")
                .build();


        contactDetailsRepository.save(contactDetails1);
        contactDetailsRepository.save(contactDetails2);

        Customer customer1 = Customer.builder()
                .name("Ivan")
                .surname("Ivanov")
                .contactDetails(contactDetails1)
                .country(country)
                .build();
        Customer customer2 = Customer.builder()
                .name("Ivan")
                .surname("Alekseev")
                .contactDetails(contactDetails2)
                .country(country)
                .build();
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        //when

        List<Customer> foundCustomers = customerRepository.findAll();

        //then

        assertFalse(foundCustomers.isEmpty());
        assertTrue(foundCustomers.contains(customer1));
        assertTrue(foundCustomers.contains(customer2));
    }

    @Test
    public void testFindCustomerById() {

        //given

        Country country = countryRepository.findByCountryCode("RUS").get();

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

    @Test
    public void testFindCustomersByCountry() {

        //given

        Country country = countryRepository.findByCountryCode("RUS").get();

        ContactDetails contactDetails1 = ContactDetails.builder()
                .email("iprody08@gmail.com")
                .telegramId("@iprody08")
                .build();
        ContactDetails contactDetails2 = ContactDetails.builder()
                .email("iprody008@gmail.com")
                .telegramId("@iprody008")
                .build();


        contactDetailsRepository.save(contactDetails1);
        contactDetailsRepository.save(contactDetails2);

        Customer customer1 = Customer.builder()
                .name("Ivan")
                .surname("Ivanov")
                .contactDetails(contactDetails1)
                .country(country)
                .build();
        Customer customer2 = Customer.builder()
                .name("Ivan")
                .surname("Alekseev")
                .contactDetails(contactDetails2)
                .country(country)
                .build();
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        //when

        List<Customer> foundCustomers = customerRepository.findCustomersByCountry(country.getCountryCode());

        //then

        assertFalse(foundCustomers.isEmpty());
        assertTrue(foundCustomers.contains(customer1));
        assertTrue(foundCustomers.contains(customer2));

    }

    @Test
    public void testFindByContactDetailsEmail() {

        //given

        Country country = countryRepository.findByCountryCode("RUS").get();

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

        Optional<Customer> foundCustomer = customerRepository.findByContactDetailsEmail(contactDetails.getEmail());

        //then

        assertFalse(foundCustomer.isEmpty());
        assertThat(foundCustomer.get()).isEqualTo(customer);

    }

}
