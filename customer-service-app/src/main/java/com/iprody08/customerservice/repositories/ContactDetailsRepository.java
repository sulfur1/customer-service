package com.iprody08.customerservice.repositories;

import com.iprody08.customerservice.entities.ContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactDetailsRepository extends JpaRepository<ContactDetails, Long> {
    Optional<ContactDetails> findByEmail(String email);
    Optional<ContactDetails> findByTelegramId(String telegramId);
}
