package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.CountryDTO;
import com.iprody08.customerservice.dto.mapper.CountryMapper;
import com.iprody08.customerservice.repositories.CountryRepository;
import com.iprody08.customerservice.services.CountryService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Transactional
    public Optional<CountryDTO> findCountryById(long id) {
        return countryRepository.findById(id) //todo add factory validator
                .map(countryMapper::countryToDTO);
    }

    @Override
    @Transactional
    public List<CountryDTO> findAll() {
        return countryRepository.findAll().stream() //todo add factory validator
                .map(countryMapper::countryToDTO)
                .collect(Collectors.toList());
    }
}
