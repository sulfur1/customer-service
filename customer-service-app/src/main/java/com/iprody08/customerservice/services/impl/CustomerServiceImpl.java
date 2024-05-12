package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.CustomerDto;
import com.iprody08.customerservice.mapper.CustomerMapper;
import com.iprody08.customerservice.entities.ContactDetails;
import com.iprody08.customerservice.entities.Country;
import com.iprody08.customerservice.entities.Customer;
import com.iprody08.customerservice.repositories.ContactDetailsRepository;
import com.iprody08.customerservice.repositories.CountryRepository;
import com.iprody08.customerservice.repositories.CustomerRepository;
import com.iprody08.customerservice.services.CustomerService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private static final String ERROR_CUSTOMER_EXISTS_MESSAGE = "Customer %s already exists ";
    private static final String ERROR_CUSTOMER_NOT_FOUND_MESSAGE = "Customer with id %d not found";
    private static final String ERROR_COUNTRY_NOT_FOUND_MESSAGE = "Country with id %d not found";

    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;
    private final ContactDetailsRepository contactDetailsRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CountryRepository countryRepository,
                               ContactDetailsRepository contactDetailsRepository,
                               CustomerMapper customerMapper
    ) {
        this.customerRepository = customerRepository;
        this.countryRepository = countryRepository;
        this.contactDetailsRepository = contactDetailsRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    @Transactional
    public CustomerDto addCustomer(CustomerDto customerDto) {
        if (customerDto.getId() != null && customerRepository.existsById(customerDto.getId())) {
            log.error(String.format(ERROR_CUSTOMER_NOT_FOUND_MESSAGE, customerDto.getId()));
            throw new EntityExistsException(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, customerDto.getId()));
        }

        if (contactDetailsRepository.findByTelegramId(customerDto.getTelegramId()).isPresent()) {
            log.error(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, customerDto.getTelegramId()));
            throw new EntityExistsException(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, customerDto.getTelegramId()));
        }

        if (contactDetailsRepository.findByEmail(customerDto.getEmail()).isPresent()) {
            log.error(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, customerDto.getEmail()));
            throw new EntityExistsException(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, customerDto.getEmail()));
        }

        if (!countryRepository.existsById(customerDto.getCountryId())) {
            log.error(String.format(ERROR_COUNTRY_NOT_FOUND_MESSAGE, customerDto.getCountryId()));
            throw new EntityNotFoundException(
                    String.format(ERROR_COUNTRY_NOT_FOUND_MESSAGE, customerDto.getCountryId()));
        }

        Customer customer = customerMapper.dtoToCustomer(customerDto);
        ContactDetails contactDetails = customer.getContactDetails();
        contactDetailsRepository.save(contactDetails);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToDto(savedCustomer);
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        return customerRepository.findById(customerDto.getId())
                .map(customer -> {
                    customer.setName(customerDto.getName());
                    customer.setSurname(customerDto.getSurname());
                    if (customerDto.getCountryId() != null) {
                        Country country = countryRepository.findById(customerDto.getCountryId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                        String.format(ERROR_COUNTRY_NOT_FOUND_MESSAGE, customerDto.getCountryId())));
                        customer.setCountry(country);
                    }
                    if (contactDetailsRepository.findByTelegramId(customerDto.getTelegramId()).isEmpty()) {
                        customer.getContactDetails().setTelegramId(customerDto.getTelegramId());
                    }
                    if (contactDetailsRepository.findByEmail(customerDto.getEmail()).isEmpty()) {
                        customer.getContactDetails().setEmail(customerDto.getEmail());
                    }

                    customer.getContactDetails().setUpdatedAt(Instant.now());
                    customer.setUpdatedAt(Instant.now());

                    Customer savedCustomer = customerRepository.save(customer);
                    return customerMapper.customerToDto(savedCustomer);
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ERROR_CUSTOMER_NOT_FOUND_MESSAGE, customerDto.getId())));
    }

    @Override
    public Optional<CustomerDto> findCustomerById(long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToDto);
    }

    @Override
    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerDto> findAllCustomers(Pageable pageable) {
        Page<Customer> customersPage = customerRepository.findAll(pageable);
        return customersPage.map(customerMapper::customerToDto).toList();
    }


    @Override
    public Optional<CustomerDto> findCustomerByEmail(String email) {
        return customerRepository.findByContactDetailsEmail(email)
                .map(customerMapper::customerToDto);
    }

    @Override
    public Optional<CustomerDto> findCustomerByTelegramId(String telegramId) {
        return customerRepository.findByContactsDetailsTelegramId(telegramId)
                .map(customerMapper::customerToDto);
    }

    @Override
    public List<CustomerDto> findCustomerByName(String name, Pageable pageable) {
        return customerRepository.findCustomersByName(name).stream()
                .map(customerMapper::customerToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDto> findCustomerBySurname(String surname, Pageable pageable) {
        return customerRepository.findCustomersBySurname(surname).stream()
                .map(customerMapper::customerToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDto> findCustomersByCountry(String country, Pageable pageable) {
        return customerRepository.findCustomersByCountry(country).stream()
                .map(customerMapper::customerToDto)
                .collect(Collectors.toList());
    }

}
