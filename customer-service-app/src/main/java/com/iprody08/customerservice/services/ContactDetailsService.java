package com.iprody08.customerservice.services;

import com.iprody08.customerservice.dto.ContactDetailsDTO;
import java.util.List;
import java.util.Optional;

public interface ContactDetailsService {

    ContactDetailsDTO save(ContactDetailsDTO contactDetailsDTO);

    Optional<ContactDetailsDTO> update(ContactDetailsDTO contactDetailsDTO);
    Optional<ContactDetailsDTO> findContactsById(long id);

    void delete(long id);

    List<ContactDetailsDTO> findAll();

}
