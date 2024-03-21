package com.iprody08.customerservice.services;

import com.iprody08.customerservice.dto.CustomerDTO;
import java.util.List;
import java.util.Optional;

public interface CustomerService {

    CustomerDTO save(CustomerDTO customerDTO);

    Optional<CustomerDTO> update(CustomerDTO customerDTO);

    Optional<CustomerDTO> findById(Long id);

    void delete(Long id);

    List<CustomerDTO> findAll();

}
