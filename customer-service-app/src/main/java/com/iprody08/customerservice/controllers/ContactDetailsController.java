package com.iprody08.customerservice.controllers;

import com.iprody08.customerservice.dto.ContactDetailsDto;
import com.iprody08.customerservice.errors.NotFoundException;
import com.iprody08.customerservice.services.ContactDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/contact_details", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactDetailsController {

    private final ContactDetailsService contactDetailsService;

    @Autowired
    public ContactDetailsController(ContactDetailsService contactDetailsService) {
        this.contactDetailsService = contactDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<ContactDetailsDto>> getAllContactDetails(Pageable pageable) {
        List<ContactDetailsDto> contactDetailsList = contactDetailsService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(contactDetailsList);
    }

    @PostMapping
    public ResponseEntity<ContactDetailsDto> addContactDetails(@Valid @RequestBody ContactDetailsDto contactDetailsDto) {

        ContactDetailsDto savedContactDetails = contactDetailsService.save(contactDetailsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContactDetails);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactDetailsDto getContactDetailsById(@PathVariable long id) {
        return contactDetailsService
                .findContactsById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Contact details not found by id: %s", id)
                ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDetailsDto> updateContactDetails(@PathVariable long id,
                                                                  @Valid @RequestBody ContactDetailsDto contactDetailsDto) {
        contactDetailsDto.setId(id);
        ContactDetailsDto contactDetails = contactDetailsService.update(contactDetailsDto);
        return ResponseEntity.status(HttpStatus.OK).body(contactDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContactDetails(@PathVariable long id) {
        contactDetailsService.delete(id);
    }
}
