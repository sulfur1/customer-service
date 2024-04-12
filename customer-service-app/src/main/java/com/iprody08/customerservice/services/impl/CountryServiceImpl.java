package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.CountryDto;
import com.iprody08.customerservice.dto.mapper.CountryMapper;
import com.iprody08.customerservice.repositories.CountryRepository;
import com.iprody08.customerservice.services.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    private final CountryMapper countryMapper;
    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryMapper countryMapper, CountryRepository countryRepository) {
        this.countryMapper = countryMapper;
        this.countryRepository = countryRepository;
    }

    @Override
    public Optional<CountryDto> findCountryById(long id) {
        return countryRepository.findById(id)
                .map(countryMapper::countryToDto);
    }

    @Override
    public List<CountryDto> findAll(Pageable pageable) {
        return countryRepository.findAll().stream()
                .map(countryMapper::countryToDto)
                .collect(Collectors.toList());
    }
}
