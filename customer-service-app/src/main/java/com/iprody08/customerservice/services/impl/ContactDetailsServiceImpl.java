package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.ContactDetailsDto;
import com.iprody08.customerservice.dto.mapper.ContactDetailsMapper;
import com.iprody08.customerservice.entities.ContactDetails;
import com.iprody08.customerservice.repositories.ContactDetailsRepository;
import com.iprody08.customerservice.services.ContactDetailsService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContactDetailsServiceImpl implements ContactDetailsService {
    private static final String ERROR_EXISTS_MESSAGE = "Contact details with id %d already exists";
    private static final String ERROR_NOT_FOUND_MESSAGE = "Contact details with id %d not found";
    private final ContactDetailsMapper mapper;
    private final ContactDetailsRepository repository;

    @Autowired
    public ContactDetailsServiceImpl(ContactDetailsMapper mapper, ContactDetailsRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ContactDetailsDto save(ContactDetailsDto contactDetailsDto) {
        if (contactDetailsDto.getId() != null && repository.existsById(contactDetailsDto.getId())) {
            log.error(String.format(ERROR_EXISTS_MESSAGE, contactDetailsDto.getId()));
            throw new EntityExistsException(String.format(ERROR_EXISTS_MESSAGE, contactDetailsDto.getId()));
        }
        ContactDetails entity = mapper.dtoToEntity(contactDetailsDto);
        return mapper.entityToDto(repository.save(entity));
    }

    @Override
    public ContactDetailsDto update(ContactDetailsDto contactDetailsDto) {
        return repository.findById(contactDetailsDto.getId())
                .map(contactDetails -> {
                    contactDetails.setEmail(contactDetailsDto.getEmail());
                    contactDetails.setTelegramId(contactDetailsDto.getTelegramId());
                    contactDetails.setUpdatedAt(Instant.now());
                    repository.save(contactDetails);
                    return mapper.entityToDto(contactDetails);
                })
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ERROR_NOT_FOUND_MESSAGE, contactDetailsDto.getId())));
    }

    @Override
    public Optional<ContactDetailsDto> findContactsById(long id) {
        return repository.findById(id)
                .map(mapper::entityToDto);
    }

    @Override

    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ContactDetailsDto> findAll(Pageable pageable) {
        return repository.findAll().stream()
                .map(mapper::entityToDto).toList();
    }
}
