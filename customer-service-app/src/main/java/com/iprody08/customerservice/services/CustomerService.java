package com.iprody08.customerservice.services;

import com.iprody08.customerservice.dto.CustomerDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerDto addCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(CustomerDto customerDto);

    Optional<CustomerDto> findCustomerById(long id);

    void deleteCustomer(long id);

    List<CustomerDto> findAllCustomers(Pageable pageable);

    Optional<CustomerDto> findCustomerByEmail(String email);

    Optional<CustomerDto> findCustomerByTelegramId(String telegramId);

    List<CustomerDto> findCustomerByName(String name, Pageable pageable);

    List<CustomerDto> findCustomerBySurname(String surname, Pageable pageable);

    List<CustomerDto> findCustomersByCountry(String country, Pageable pageable);

}
