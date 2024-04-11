package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.CountryDto;
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

import static com.iprody08.customerservice.for_tests.CustomerServiceConstants.LV;
import static com.iprody08.customerservice.for_tests.CustomerServiceConstants.US;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

    private long id;

    private Country country;

    @Mock
    private CountryRepository countryRepository;

    @Spy
    private CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);

    @InjectMocks
    private CountryServiceImpl service;

    @BeforeEach
    void setUp() {
        id = US.getId();
        country = US;
    }

    @Test
    void countryServiceImplFindCountryById() {
        // given
        when(countryRepository.findById(id)).thenReturn(Optional.of(country));

        // when
        Optional<CountryDto> result = service.findCountryById(id);

        // then
        assertTrue(result.isPresent(), "CountryDto should be present");
        assertEquals(country.getCountryCode(), result.get().getCountryCode());
        assertEquals(country.getName(), result.get().getName());
    }

    @Test
    public void countryServiceImplFindCountryByIdWhenIdDoesNotExist() {
        // given
        when(countryRepository.findById(id)).thenReturn(Optional.empty());

        // when
        Optional<CountryDto> result = service.findCountryById(id);

        // then
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void countryServiceImplFindAll() {
        // given
        List<Country> countries = List.of(US, LV);
        when(countryRepository.findAll()).thenReturn(countries);

        // when
        List<CountryDto> result = service.findAll(null);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertEquals(countries.get(0).getId(), result.get(0).getId());
        assertEquals(countries.get(0).getCountryCode(), result.get(0).getCountryCode());
        assertEquals(countries.get(0).getName(), result.get(0).getName());
        assertEquals(countries.get(1).getId(), result.get(1).getId());
        assertEquals(countries.get(1).getCountryCode(), result.get(1).getCountryCode());
        assertEquals(countries.get(1).getName(), result.get(1).getName());
    }
}
