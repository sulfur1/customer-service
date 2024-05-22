package com.iprody08.customerservice.controllers;

import com.iprody08.customerservice.dto.CountryDto;
import com.iprody08.customerservice.services.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/countries", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @Operation(summary = "Get all countries", description = "Returns all countries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Countries found"),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CountryDto> getAllCountries(@PageableDefault(size = 248) Pageable pageable) {
        log.info("Get all countries");
        return countryService.findAll(pageable);
    }
}
