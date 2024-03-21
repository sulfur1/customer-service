package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.ContactDetailsDTO;
import com.iprody08.customerservice.dto.mapper.ContactDetailsMapper;
import com.iprody08.customerservice.entities.ContactDetails;
import com.iprody08.customerservice.repositories.ContactDetailsRepository;
import com.iprody08.customerservice.services.ContactDetailsService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContactDetailsServiceImpl implements ContactDetailsService {
    private final ContactDetailsMapper mapper;
    private final ContactDetailsRepository repository;

    private final String ERROR_EXISTS_MESSAGE = "Contact details with id %d already exists";
    private final String ERROR_NOT_FOUND_MESSAGE = "Contact details with id %d not found";

    @Autowired
    public ContactDetailsServiceImpl(ContactDetailsMapper mapper, ContactDetailsRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    @Transactional
    public ContactDetailsDTO save(ContactDetailsDTO dto) {
        if (dto.getId() != null && repository.existsById(dto.getId())) { //todo replace with factory validator
            log.error(String.format(ERROR_EXISTS_MESSAGE, dto.getId()));
            throw new EntityExistsException(String.format(ERROR_EXISTS_MESSAGE, dto.getId()));
        }
        ContactDetails entity = mapper.dtoToEntity(dto);
        return mapper.entityToDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public Optional<ContactDetailsDTO> update(ContactDetailsDTO dto) {
        repository.findById(dto.getId()) //todo replace with factory validator
                .orElseThrow(() -> new EntityNotFoundException(String.format(ERROR_NOT_FOUND_MESSAGE, dto.getId())));
        return repository.findById(dto.getId())
                .map(contactDetails -> {
                    contactDetails.setEmail(dto.getEmail());
                    contactDetails.setTelegramId(dto.getTelegramId());
                    return mapper.entityToDTO(contactDetails);
                });
    }

    @Override
    @Transactional
    public Optional<ContactDetailsDTO> findContactsById(long id) {
        return repository.findById(id) //todo add factory validator
                .map(mapper::entityToDTO);
    }

    @Override
    @Transactional
    public void delete(long id) {
        repository.deleteById(id); //todo add factory validator
    }

    @Override
    @Transactional
    public List<ContactDetailsDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::entityToDTO)
                .collect(Collectors.toList());
    }
}
