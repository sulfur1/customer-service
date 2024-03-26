package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.CustomerDTO;
import com.iprody08.customerservice.dto.mapper.ContactDetailsMapper;
import com.iprody08.customerservice.dto.mapper.CountryMapper;
import com.iprody08.customerservice.dto.mapper.CustomerMapper;
import com.iprody08.customerservice.entities.Customer;
import com.iprody08.customerservice.repositories.CountryRepository;
import com.iprody08.customerservice.repositories.CustomerRepository;
import com.iprody08.customerservice.services.CustomerService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final String ERROR_CUSTOMER_EXISTS_MESSAGE = "Customer with id %d already exists";
    private static final String ERROR_CUSTOMER_NOT_FOUND_MESSAGE = "Customer with id %d not found";
    private static final String ERROR_COUNTRY_NOT_FOUND_MESSAGE = "Country with id %d not found";
    private static final String ERROR_CONTACTS_DETAILS_MESSAGE = "Contacts details have errors";

    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;
    private final CustomerMapper customerMapper;
    private final CountryMapper countryMapper;
    private final ContactDetailsMapper contactDetailsMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CountryRepository countryRepository,
                               CustomerMapper customerMapper,
                               CountryMapper countryMapper,
                               ContactDetailsMapper contactDetailsMapper) {
        this.customerRepository = customerRepository;
        this.countryRepository = countryRepository;
        this.customerMapper = customerMapper;
        this.countryMapper = countryMapper;
        this.contactDetailsMapper = contactDetailsMapper;
    }

    @Override
    @Transactional
    public CustomerDTO saveCustomer(CustomerDTO dto) {
        if (dto.getId() != null && customerRepository.existsById(dto.getId())) {
            log.error(String.format(ERROR_CUSTOMER_NOT_FOUND_MESSAGE, dto.getId()));
            throw new EntityExistsException(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, dto.getId()));
        }

        if (dto.getContactDetails() == null) {
            log.error(String.format(String.format(ERROR_CONTACTS_DETAILS_MESSAGE)));
            throw new EntityNotFoundException(String.format(ERROR_CONTACTS_DETAILS_MESSAGE));
        }

        Long countryId = dto.getCountry().getId();
        if (!countryRepository.existsById(countryId)) {
            log.error(String.format(ERROR_COUNTRY_NOT_FOUND_MESSAGE, countryId));
            throw new EntityNotFoundException(String.format(ERROR_COUNTRY_NOT_FOUND_MESSAGE, countryId));
        }

        Customer customer = customerMapper.dtoToCustomer(dto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO dto) {
        return customerRepository.findById(dto.getId())
                .map(customer -> {
                    customer.setName(dto.getName());
                    customer.setSurname(dto.getSurname());
                    customer.setCountry(countryMapper.dtoToCountry(dto.getCountry()));
                    customer.getContactDetails().setEmail(dto.getContactDetails().getEmail());
                    customer.getContactDetails().setTelegramId(dto.getContactDetails().getTelegramId());
                    customerRepository.save(customer);
                    return customerMapper.customerToDTO(customer);
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ERROR_CUSTOMER_NOT_FOUND_MESSAGE, dto.getId())));
    }

    @Override
    public Optional<CustomerDTO> findCustomerById(long id) {
        return customerRepository.findById(id)
                .map(customerMapper::customerToDTO);
    }

    @Override
    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerDTO> findAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToDTO)
                .collect(Collectors.toList());
    }
}
