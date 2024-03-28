package com.iprody08.customerservice.dto.mapper;

import com.iprody08.customerservice.dto.CustomerDTO;
import com.iprody08.customerservice.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Customer dtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToDTO(Customer customer);

}
