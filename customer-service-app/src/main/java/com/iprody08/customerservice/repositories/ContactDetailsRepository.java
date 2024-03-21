package com.iprody08.customerservice.repositories;

import com.iprody08.customerservice.entities.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {
}
