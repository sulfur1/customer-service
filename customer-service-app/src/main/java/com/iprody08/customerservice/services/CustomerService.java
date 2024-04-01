package com.iprody08.customerservice.services;

import com.iprody08.customerservice.dto.CustomerDTO;
import com.iprody08.customerservice.dto.CustomerListParams;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerDTO addCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    Optional<CustomerDTO> findCustomerById(long id);

    void deleteCustomer(long id);

    Page<CustomerDTO> findAllCustomers(CustomerListParams customerListParams);

    Optional<CustomerDTO> findCustomerByEmail(String email);
    Optional<CustomerDTO> findCustomerByTelegramId(String telegramId);

    List<CustomerDTO> findCustomerByName(String name);
    List<CustomerDTO> findCustomerBySurname(String surname);
    List<CustomerDTO> findCustomersByCountry(String country);
}
