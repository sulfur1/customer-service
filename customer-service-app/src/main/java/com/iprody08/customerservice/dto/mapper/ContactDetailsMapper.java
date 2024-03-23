package com.iprody08.customerservice.dto.mapper;

import com.iprody08.customerservice.dto.ContactDetailsDTO;
import com.iprody08.customerservice.entities.ContactDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactDetailsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ContactDetails dtoToEntity(ContactDetailsDTO contactDetailsDTO);

    ContactDetailsDTO entityToDTO(ContactDetails contactDetails);

}
