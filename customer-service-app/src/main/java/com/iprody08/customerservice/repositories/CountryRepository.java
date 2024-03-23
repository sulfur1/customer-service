package com.iprody08.customerservice.repositories;

import com.iprody08.customerservice.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
