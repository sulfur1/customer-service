package com.iprody08.customerservice.services;

import com.iprody08.customerservice.dto.CountryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    Optional<CountryDto> findCountryById(long id);

    List<CountryDto> findAll(Pageable pageable);

}
