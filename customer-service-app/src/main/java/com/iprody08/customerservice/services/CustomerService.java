package com.iprody08.customerservice.services;

import com.iprody08.customerservice.dto.CustomerDTO;
import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    Optional<CustomerDTO> findCustomerById(long id);

    void deleteCustomer(long id);

    List<CustomerDTO> findAllCustomers();

}
