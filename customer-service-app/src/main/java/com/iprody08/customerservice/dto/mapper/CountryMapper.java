package com.iprody08.customerservice.dto.mapper;

import com.iprody08.customerservice.dto.CountryDTO;
import com.iprody08.customerservice.entities.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Country dtoToCountry(CountryDTO countryDTO);

    CountryDTO countryToDTO(Country country);

}