package com.iprody08.customerservice.dto.mapper;

import com.iprody08.customerservice.dto.ContactDetailsDTO;
import com.iprody08.customerservice.entities.ContactDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContactDetailsMapper {

    ContactDetailsMapper INSTANCE = Mappers.getMapper(ContactDetailsMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ContactDetails dtoToEntity(ContactDetailsDTO contactDetailsDTO);

    ContactDetailsDTO entityToDTO(ContactDetails contactDetails);

}
