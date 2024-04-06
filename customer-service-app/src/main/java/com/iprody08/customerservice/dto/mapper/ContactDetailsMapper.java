package com.iprody08.customerservice.dto.mapper;

import com.iprody08.customerservice.dto.ContactDetailsDto;
import com.iprody08.customerservice.entities.ContactDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactDetailsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ContactDetails dtoToEntity(ContactDetailsDto contactDetailsDTO);


    @Mapping(target = "customerId", source = "customer.id")
    ContactDetailsDto entityToDTO(ContactDetails contactDetails);

}
