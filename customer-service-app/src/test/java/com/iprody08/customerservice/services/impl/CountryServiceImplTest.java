package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.CountryDTO;
import com.iprody08.customerservice.dto.mapper.CountryMapper;
import com.iprody08.customerservice.entities.Country;
import com.iprody08.customerservice.repositories.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {
    private static final long ID = 1L;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CountryMapper countryMapper;

    @InjectMocks
    private CountryServiceImpl service;

    @Test
    void findEventShouldCallRepository() {

        // given
        Country country = mock(Country.class);
        CountryDTO expected = mock(CountryDTO.class);
        when(countryRepository.findById(ID)).thenReturn(Optional.of(country));
        when(countryMapper.countryToDTO(country)).thenReturn(expected);

        // when
        Optional<CountryDTO> countryDTOOptional = service.findCountryById(ID);
        var result = countryDTOOptional.get();

        // then
        assertEquals(expected, result);
        verify(countryRepository, times(1)).findById(ID);
        verify(countryMapper, times(1)).countryToDTO(country);
    }

    @Test
    public void testFindCountryByIdWhenIdDoesNotExist() {
        // given
        when(countryRepository.findById(ID)).thenReturn(Optional.empty());

        // when
        Optional<CountryDTO> result = service.findCountryById(ID);

        // then
        assertEquals(Optional.empty(), result);
        verify(countryRepository, times(1)).findById(ID);
        verifyNoInteractions(countryMapper);
    }

    @Test
    public void testFindAll() {
        // given
        List<Country> countries = new ArrayList<>();
        countries.add(new Country());
        countries.add(new Country());
        when(countryRepository.findAll()).thenReturn(countries);
        List<CountryDTO> expectedDTOs = new ArrayList<>();
        expectedDTOs.add(new CountryDTO());
        expectedDTOs.add(new CountryDTO());
        when(countryMapper.countryToDTO(any())).thenReturn(new CountryDTO());

        // when
        List<CountryDTO> result = service.findAll(null);

        // then
        assertEquals(expectedDTOs.size(), result.size());
        verify(countryRepository, times(1)).findAll();
        verify(countryMapper, times(countries.size())).countryToDTO(any());
    }
}
