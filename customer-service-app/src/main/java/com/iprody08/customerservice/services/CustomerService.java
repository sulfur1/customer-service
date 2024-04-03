package com.iprody08.customerservice.services;

import com.iprody08.customerservice.dto.CustomerDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerDTO addCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    Optional<CustomerDTO> findCustomerById(long id);

    void deleteCustomer(long id);

    List<CustomerDTO> findAllCustomers(Pageable pageable);

    Optional<CustomerDTO> findCustomerByEmail(String email);

    Optional<CustomerDTO> findCustomerByTelegramId(String telegramId);

    List<CustomerDTO> findCustomerByName(String name, Pageable pageable);

    List<CustomerDTO> findCustomerBySurname(String surname, Pageable pageable);

    List<CustomerDTO> findCustomersByCountry(String country, Pageable pageable);

}
