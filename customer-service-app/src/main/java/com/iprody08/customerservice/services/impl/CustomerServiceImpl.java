package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.CustomerDTO;
import com.iprody08.customerservice.dto.CustomerListParams;
import com.iprody08.customerservice.dto.mapper.CustomerMapper;
import com.iprody08.customerservice.entities.Country;
import com.iprody08.customerservice.entities.Customer;
import com.iprody08.customerservice.repositories.CountryRepository;
import com.iprody08.customerservice.repositories.CustomerRepository;
import com.iprody08.customerservice.services.CustomerService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CountryRepository countryRepository,
                               CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.countryRepository = countryRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    @Transactional
    public CustomerDTO addCustomer(CustomerDTO dto) {
        if (dto.getId() != null && customerRepository.existsById(dto.getId())) {
            log.error(String.format(ERROR_CUSTOMER_NOT_FOUND_MESSAGE, dto.getId()));
            throw new EntityExistsException(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, dto.getId()));
        }

        if (customerRepository.existsByTelegramId(dto.getTelegramId())
                || customerRepository.existByEmail(dto.getEmail())) {
            log.error(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, dto.getTelegramId()));
            throw new EntityExistsException(String.format(ERROR_CUSTOMER_EXISTS_MESSAGE, dto.getTelegramId()));
        }

        if (!countryRepository.existsById(dto.getCountryId())) {
            log.error(String.format(ERROR_COUNTRY_NOT_FOUND_MESSAGE, dto.getCountryId()));
            throw new EntityNotFoundException(String.format(ERROR_COUNTRY_NOT_FOUND_MESSAGE, dto.getCountryId()));
        }

        Customer customer = customerMapper.dtoToCustomer(dto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToDTO(savedCustomer);
    }

    @Override
    @Transactional
    public CustomerDTO updateCustomer(CustomerDTO dto) {
        return customerRepository.findById(dto.getId())
                .map(customer -> {
                    customer.setName(dto.getName());
                    customer.setSurname(dto.getSurname());
                    if (dto.getCountryId() != null) {
                        Country country = countryRepository.findById(dto.getCountryId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                        String.format(ERROR_COUNTRY_NOT_FOUND_MESSAGE, dto.getCountryId())));
                        customer.setCountry(country);
                    }

                    customer.getContactDetails().setEmail(dto.getEmail());
                    customer.getContactDetails().setTelegramId(dto.getTelegramId());

                    customerRepository.save(customer);
                    return customerMapper.customerToDTO(customerRepository.save(customer));
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
    public Page<CustomerDTO> findAllCustomers(CustomerListParams customerListParams) {
        return customerRepository.findAll(
                PageRequest.of(
                        customerListParams.getPage(), //default on controller level with not null expectation;
                        customerListParams.getSize(),
                        Sort.by(customerListParams.getSortField())))
                .map(customerMapper::customerToDTO);
    }

    @Override
    public Optional<CustomerDTO> findCustomerByEmail(String email) {
        return customerRepository.findByContactDetailsEmail(email)
                .map(customerMapper::customerToDTO);
    }

    @Override
    public Optional<CustomerDTO> findCustomerByTelegramId(String telegramId) {
        return customerRepository.findByContactsDetailsTelegramId(telegramId)
                .map(customerMapper::customerToDTO);
    }

    @Override
    public List<CustomerDTO> findCustomerByName(String name) {
        return customerRepository.findCustomersByName(name).stream()
                .map(customerMapper::customerToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> findCustomerBySurname(String surname) {
        return customerRepository.findCustomersBySurname(surname).stream()
                .map(customerMapper::customerToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> findCustomersByCountry(String country) {
        return customerRepository.findCustomersByCountry(country).stream()
                .map(customerMapper::customerToDTO)
                .collect(Collectors.toList());
    }

}
