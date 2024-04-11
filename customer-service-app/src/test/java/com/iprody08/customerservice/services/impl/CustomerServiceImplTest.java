package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.CustomerDto;
import com.iprody08.customerservice.dto.mapper.CustomerMapper;
import com.iprody08.customerservice.entities.ContactDetails;
import com.iprody08.customerservice.entities.Country;
import com.iprody08.customerservice.entities.Customer;
import com.iprody08.customerservice.repositories.ContactDetailsRepository;
import com.iprody08.customerservice.repositories.CountryRepository;
import com.iprody08.customerservice.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.iprody08.customerservice.for_tests.CustomerServiceConstants.CONTACT_DETAILS_FIRST;
import static com.iprody08.customerservice.for_tests.CustomerServiceConstants.FIRST_CUSTOMER;
import static com.iprody08.customerservice.for_tests.CustomerServiceConstants.FIRST_CUSTOMER_DTO;
import static com.iprody08.customerservice.for_tests.CustomerServiceConstants.SECOND_CUSTOMER;
import static com.iprody08.customerservice.for_tests.CustomerServiceConstants.SECOND_CUSTOMER_DTO;
import static com.iprody08.customerservice.for_tests.CustomerServiceConstants.US;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    private Country country;

    private ContactDetails contactDetails;

    private Customer customer;

    private CustomerDto customerDto;

    private long customerId;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ContactDetailsRepository contactDetailsRepository;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CustomerServiceImpl service;

    @Spy
    private CustomerMapper customerMapper = Mappers.getMapper(CustomerMapper.class);

    @BeforeEach
    void setUp() {

        country = US;

        contactDetails = CONTACT_DETAILS_FIRST;

        customer = FIRST_CUSTOMER;

        customerId = FIRST_CUSTOMER.getId();

        customerDto = FIRST_CUSTOMER_DTO;
    }

    @Test
    void customerServiceImplFindCustomerById() {
        // given
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        // when
        Optional<CustomerDto> result = service.findCustomerById(customerId);

        // then
        assertTrue(result.isPresent(), "CustomerDto should be present");
        assertEquals(customer.getName(), result.get().getName());
        assertEquals(customer.getSurname(), result.get().getSurname());
        assertEquals(customer.getContactDetails().getEmail(), result.get().getEmail());
        assertEquals(customer.getContactDetails().getTelegramId(), result.get().getTelegramId());
        assertEquals(customer.getCountry().getId(), result.get().getCountryId());
        assertEquals(customer.getCountry().getName(), result.get().getCountryName());
    }

    @Test
    public void customerServiceImplFindCustomerByIdWhenIdDoesNotExist() {
        // given
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // when
        Optional<CustomerDto> result = service.findCustomerById(customerId);

        // then
        assertFalse(result.isPresent(), "CustomerDto should not be present");
    }

    @Test
    public void customerServiceImplFindCustomerByEmailAddress() {
        // given
        when(customerRepository.findByContactDetailsEmail(customer.getContactDetails().getEmail()))
                .thenReturn(Optional.of(customer));

        // when
        Optional<CustomerDto> result = service.findCustomerByEmail(customer.getContactDetails().getEmail());

        // then
        assertTrue(result.isPresent(), "CustomerDto should be present");
        assertEquals(customer.getName(), result.get().getName());
        assertEquals(customer.getSurname(), result.get().getSurname());
        assertEquals(customer.getContactDetails().getEmail(), result.get().getEmail());
        assertEquals(customer.getContactDetails().getTelegramId(), result.get().getTelegramId());
        assertEquals(customer.getCountry().getId(), result.get().getCountryId());
        assertEquals(customer.getCountry().getName(), result.get().getCountryName());
    }

    @Test
    public void customerServiceImplFindCustomerByTelegramId() {
        // given
        when(customerRepository.findByContactsDetailsTelegramId(customer.getContactDetails().getTelegramId()))
                .thenReturn(Optional.of(customer));

        // when
        Optional<CustomerDto> result = service.findCustomerByTelegramId(customer.getContactDetails().getTelegramId());

        // then
        assertTrue(result.isPresent(), "CustomerDto should be present");
        assertEquals(customer.getName(), result.get().getName());
        assertEquals(customer.getSurname(), result.get().getSurname());
        assertEquals(customer.getContactDetails().getEmail(), result.get().getEmail());
        assertEquals(customer.getContactDetails().getTelegramId(), result.get().getTelegramId());
        assertEquals(customer.getCountry().getId(), result.get().getCountryId());
        assertEquals(customer.getCountry().getName(), result.get().getCountryName());
    }

    @Test
    public void customerServiceImplFindCustomerByName() {
        // given
        List<Customer> customersList = List.of(
                FIRST_CUSTOMER
        );
        when(customerRepository.findCustomersByName(customer.getName())).thenReturn(customersList);

        // when
        List<CustomerDto> result = service.findCustomerByName(customer.getName(), Pageable.unpaged());

        // then
        assertEquals(customersList.size(), result.size());
        assertFalse(result.isEmpty(), "CustomerDto should not be empty");
        assertEquals(customer.getName(), result.get(0).getName());
        assertEquals(customer.getSurname(), result.get(0).getSurname());
        assertEquals(customer.getContactDetails().getEmail(), result.get(0).getEmail());
        assertEquals(customer.getContactDetails().getTelegramId(), result.get(0).getTelegramId());
        assertEquals(customer.getCountry().getId(), result.get(0).getCountryId());
        assertEquals(customer.getCountry().getName(), result.get(0).getCountryName());
    }

    @Test
    public void customerServiceImplFindCustomerBySurname() {
        // given
        List<Customer> customersList = List.of(
                FIRST_CUSTOMER
        );
        when(customerRepository.findCustomersByName(customer.getSurname())).thenReturn(customersList);

        // when
        List<CustomerDto> result = service.findCustomerByName(customer.getSurname(), Pageable.unpaged());

        // then
        assertEquals(customersList.size(), result.size());
        assertFalse(result.isEmpty(), "CustomerDto should not be empty");
        assertEquals(customer.getName(), result.get(0).getName());
        assertEquals(customer.getSurname(), result.get(0).getSurname());
        assertEquals(customer.getContactDetails().getEmail(), result.get(0).getEmail());
        assertEquals(customer.getContactDetails().getTelegramId(), result.get(0).getTelegramId());
        assertEquals(customer.getCountry().getId(), result.get(0).getCountryId());
        assertEquals(customer.getCountry().getName(), result.get(0).getCountryName());
    }

    @Test
    public void customerServiceImplFindCustomersByCountry() {
        // given
        List<Customer> customersList = List.of(
                FIRST_CUSTOMER
        );
        when(customerRepository.findCustomersByCountry(country.getName())).thenReturn(customersList);

        // when
        List<CustomerDto> result = service.findCustomersByCountry(country.getName(), Pageable.unpaged());

        // then
        assertEquals(customersList.size(), result.size());
        assertFalse(result.isEmpty(), "CustomerDto should not be empty");
        assertEquals(customer.getName(), result.get(0).getName());
        assertEquals(customer.getSurname(), result.get(0).getSurname());
        assertEquals(customer.getContactDetails().getEmail(), result.get(0).getEmail());
        assertEquals(customer.getContactDetails().getTelegramId(), result.get(0).getTelegramId());
        assertEquals(customer.getCountry().getId(), result.get(0).getCountryId());
        assertEquals(customer.getCountry().getName(), result.get(0).getCountryName());
    }

    @Test
    public void customerServiceImplSaveCustomer() {
        // given
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        when(contactDetailsRepository.save(any(ContactDetails.class))).thenReturn(contactDetails);
        when(countryRepository.existsById(customerDto.getCountryId())).thenReturn(true);

        // when
        CustomerDto result = service.addCustomer(customerDto);

        // then
        assertThat(result).isNotNull();
        assertEquals(customer.getName(), result.getName());
        assertEquals(customer.getSurname(), result.getSurname());
        assertEquals(customer.getContactDetails().getEmail(), result.getEmail());
        assertEquals(customer.getContactDetails().getTelegramId(), result.getTelegramId());
        assertEquals(customer.getCountry().getId(), result.getCountryId());
        assertEquals(customer.getCountry().getName(), result.getCountryName());
    }

    @Test
    public void customerServiceImplFindAllCustomers() {
        // given
        List<Customer> customerList = List.of(
                FIRST_CUSTOMER,
                SECOND_CUSTOMER
        );
        Page<Customer> customerPage = new PageImpl<>(customerList);
        when(customerRepository.findAll(any(Pageable.class))).thenReturn(customerPage);
        List<CustomerDto> customerDtoList = List.of(
                FIRST_CUSTOMER_DTO,
                SECOND_CUSTOMER_DTO
        );

        // when
        List<CustomerDto> result = service.findAllCustomers(Pageable.ofSize(2));

        // then
        assertThat(result).isNotNull();
        assertEquals(customerDtoList.size(), result.size());
        assertEquals(customerList.get(0).getId(), result.get(0).getId());
        assertEquals(customerList.get(0).getName(), result.get(0).getName());
        assertEquals(customerList.get(0).getSurname(), result.get(0).getSurname());
        assertEquals(customerList.get(1).getId(), result.get(1).getId());
        assertEquals(customerList.get(1).getName(), result.get(1).getName());
        assertEquals(customerList.get(1).getSurname(), result.get(1).getSurname());
    }

    @Test
    public void customerServiceImplDeleteCustomer() {
        // when
        service.deleteCustomer(customerDto.getId());

        // then
        verify(customerRepository).deleteById(customerDto.getId());
    }

    @Test
    public void customerServiceImplUpdateCustomer() {
        // given
        when(customerRepository.findById(customerDto.getId())).thenReturn(Optional.of(customer));
        when(countryRepository.findById(customerDto.getCountryId())).thenReturn(Optional.of(country));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // when
        CustomerDto result = service.updateCustomer(customerDto);

        // then
        assertThat(result).isNotNull();
        assertEquals(customer.getName(), result.getName());
        assertEquals(customer.getSurname(), result.getSurname());
        assertEquals(customer.getContactDetails().getEmail(), result.getEmail());
        assertEquals(customer.getContactDetails().getTelegramId(), result.getTelegramId());
        assertEquals(customer.getCountry().getId(), result.getCountryId());
        assertEquals(customer.getCountry().getName(), result.getCountryName());
    }

}
