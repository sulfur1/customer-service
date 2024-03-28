package com.iprody08.customerservice.repositories;

import com.iprody08.customerservice.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CountryRepository extends JpaRepository<Country, Long> {



}
