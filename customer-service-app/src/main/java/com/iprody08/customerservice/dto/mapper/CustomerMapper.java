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
    @Mapping(target = "country.id", source = "countryId")
    @Mapping(target = "contactDetails.email", source = "email")
    @Mapping(target = "contactDetails.telegramId", source = "telegramId")
    Customer dtoToCustomer(CustomerDTO customerDTO);

    @Mapping(target = "countryId", source = "country.id")
    @Mapping(target = "countryName", source = "country.name")
    @Mapping(target = "email", source = "contactDetails.email")
    @Mapping(target = "telegramId", source = "contactDetails.telegramId")
    @Mapping(target = "createdContactDetailsAt", source = "contactDetails.createdAt")
    @Mapping(target = "updatedContactDetailsAt", source = "contactDetails.updatedAt")
    @Mapping(target = "createdCustomerAt", source = "createdAt")
    @Mapping(target = "updatedCustomerAt", source = "updatedAt")
    CustomerDTO customerToDTO(Customer customer);

}
