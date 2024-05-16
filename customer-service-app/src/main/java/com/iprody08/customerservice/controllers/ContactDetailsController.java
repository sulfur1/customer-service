package com.iprody08.customerservice.controllers;

import com.iprody08.customerservice.dto.ContactDetailsDto;
import com.iprody08.customerservice.errors.NotFoundException;
import com.iprody08.customerservice.services.ContactDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

import java.util.List;

@RestController
@RequestMapping(value = "api/contact_details", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactDetailsController {

    private final ContactDetailsService contactDetailsService;

    @Autowired
    public ContactDetailsController(ContactDetailsService contactDetailsService) {
        this.contactDetailsService = contactDetailsService;
    }

    @Operation(summary = "Get contact details", description = "Returns all contact details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact details found")
    })
    @GetMapping
    public ResponseEntity<List<ContactDetailsDto>> getAllContactDetails(Pageable pageable) {
        List<ContactDetailsDto> contactDetailsList = contactDetailsService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(contactDetailsList);
    }

    @Operation(summary = "Add new contact details", description = "Added contact details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contact details created")
    })
    @PostMapping
    public ResponseEntity<ContactDetailsDto> addContactDetails(@Valid @RequestBody ContactDetailsDto contactDetailsDto) {

        ContactDetailsDto savedContactDetails = contactDetailsService.save(contactDetailsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContactDetails);
    }

    @Operation(summary = "Get contact details by ID", description = "Returns the contact details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact details found"),
            @ApiResponse(responseCode = "404", description = "Contact details not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactDetailsDto getContactDetailsById(@PathVariable long id) {
        return contactDetailsService
                .findContactsById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Contact details not found by id: %s", id)
                ));
    }

    @Operation(summary = "Update contact details by ID", description = "Returns the updated contact details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact details updated"),
            @ApiResponse(responseCode = "404", description = "Contact details not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ContactDetailsDto> updateContactDetails(@PathVariable long id,
                                                                  @Valid @RequestBody ContactDetailsDto contactDetailsDto) {
        contactDetailsDto.setId(id);
        ContactDetailsDto contactDetails = contactDetailsService.update(contactDetailsDto);
        return ResponseEntity.status(HttpStatus.OK).body(contactDetails);
    }

    @Operation(summary = "Delete contact details by ID", description = "Deletes the contact details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contact details deleted"),
            @ApiResponse(responseCode = "404", description = "Contact details not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContactDetails(@PathVariable long id) {
        contactDetailsService.delete(id);
    }
}
