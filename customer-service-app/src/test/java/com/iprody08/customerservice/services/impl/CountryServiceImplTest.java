package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.CountryDTO;
import com.iprody08.customerservice.dto.mapper.CountryMapper;
import com.iprody08.customerservice.entities.Country;
import com.iprody08.customerservice.repositories.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {
    private static final long ID = 1L;
    private Country country = new Country();

    @Mock
    private CountryRepository countryRepository;
    @Spy
    private CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);

    @InjectMocks
    private CountryServiceImpl service;

    @BeforeEach
    void setUp() {
        country.setId(ID);
        country.setCountryCode("US");
        country.setName("United States");
    }

        @Test
        void testCountryServiceImplFindCountryByIdReturnCountryDto() {
            // given
            when(countryRepository.findById(ID)).thenReturn(Optional.of(country));

            // when
            Optional<CountryDTO> result = service.findCountryById(ID);

            // then
            assertTrue(result.isPresent(), "CountryDto should be present");
            assertEquals(country.getCountryCode(), result.get().getCountryCode());
            assertEquals(country.getName(), result.get().getName());
        }

    @Test
    public void testCountryServiceImplFindCountryByIdWhenIdDoesNotExist() {
        // given
        when(countryRepository.findById(ID)).thenReturn(Optional.empty());

        // when
        Optional<CountryDTO> result = service.findCountryById(ID);

        // then
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testCountryServiceImplFindAll() {
        // given
        List<Country> countries = List.of(
                new Country(),
                new Country());
        when(countryRepository.findAll()).thenReturn(countries);
        List<CountryDTO> expectedDTOs = List.of(
                new CountryDTO(),
                new CountryDTO());

        // when
        List<CountryDTO> result = service.findAll(null);

        // then
        assertEquals(expectedDTOs.size(), result.size());
    }
}
