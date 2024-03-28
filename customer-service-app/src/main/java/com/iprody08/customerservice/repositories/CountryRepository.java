package com.iprody08.customerservice.repositories;

import com.iprody08.customerservice.entities.Country;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Transactional(readOnly = true)
public interface CountryRepository extends JpaRepository<Country, Long> {



}
