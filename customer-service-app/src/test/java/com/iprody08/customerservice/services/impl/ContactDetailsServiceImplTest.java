package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.ContactDetailsDto;
import com.iprody08.customerservice.dto.mapper.ContactDetailsMapper;
import com.iprody08.customerservice.entities.ContactDetails;
import com.iprody08.customerservice.repositories.ContactDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.iprody08.customerservice.for_tests.CustomerServiceConstants.CONTACT_DETAILS_FIRST;
import static com.iprody08.customerservice.for_tests.CustomerServiceConstants.CONTACT_DETAILS_SECOND;
import static com.iprody08.customerservice.for_tests.CustomerServiceConstants.FIRST_CONTACT_DETAILS_DTO;
import static com.iprody08.customerservice.for_tests.CustomerServiceConstants.SECOND_CONTACT_DETAILS_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ContactDetailsServiceImplTest {

    private ContactDetails contactDetails;

    private ContactDetailsDto contactDetailsDto;

    private long contactDetailsId;

    @Mock
    private ContactDetailsRepository contactDetailsRepository;

    @Spy
    private ContactDetailsMapper contactDetailsMapper = Mappers.getMapper(ContactDetailsMapper.class);

    @InjectMocks
    private ContactDetailsServiceImpl service;

    @BeforeEach
    void setUp() {

        contactDetails = CONTACT_DETAILS_FIRST;

        contactDetailsDto = FIRST_CONTACT_DETAILS_DTO;

        contactDetailsId = contactDetails.getId();
    }


    @Test
    void contactDetailsServiceImplFindContactDetailsById() {
        // given
        when(contactDetailsRepository.findById(contactDetailsId)).thenReturn(Optional.of(contactDetails));

        // when
        Optional<ContactDetailsDto> result = service.findContactsById(contactDetailsId);

        // then
        assertTrue(result.isPresent(), "ContactDetailsDto should be present");
        assertEquals(contactDetails.getEmail(), result.get().getEmail());
        assertEquals(contactDetails.getTelegramId(), result.get().getTelegramId());
    }

    @Test
    public void contactDetailsServiceImplFindContactDetailsByIdWhenIdDoesNotExist() {
        // given
        when(contactDetailsRepository.findById(contactDetailsId)).thenReturn(Optional.empty());

        // when
        Optional<ContactDetailsDto> result = service.findContactsById(contactDetailsId);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void contactDetailsServiceImplFindAllContactDetails() {
        // given
        List<ContactDetails> contactDetailsList = List.of(
                CONTACT_DETAILS_FIRST,
                CONTACT_DETAILS_SECOND);
        when(contactDetailsRepository.findAll()).thenReturn(contactDetailsList);
        List<ContactDetailsDto> expectedDTOs = List.of(
                FIRST_CONTACT_DETAILS_DTO,
                SECOND_CONTACT_DETAILS_DTO);

        // when
        List<ContactDetailsDto> result = service.findAll(Pageable.unpaged());

        // then
        assertEquals(expectedDTOs.size(), result.size());
        assertEquals(contactDetailsList.getFirst().getId(), result.getFirst().getId());
        assertEquals(contactDetailsList.getFirst().getEmail(), result.getFirst().getEmail());
        assertEquals(contactDetailsList.getFirst().getTelegramId(), result.getFirst().getTelegramId());
        assertEquals(contactDetailsList.get(1).getId(), result.get(1).getId());
        assertEquals(contactDetailsList.get(1).getEmail(), result.get(1).getEmail());
        assertEquals(contactDetailsList.get(1).getTelegramId(), result.get(1).getTelegramId());
    }

    @Test
    void contactDetailsServiceImplSaveContactDetails() {
        // given
        when(contactDetailsRepository.save(any(ContactDetails.class))).thenReturn(contactDetails);

        // when
        ContactDetailsDto result = service.save(contactDetailsDto);

        // then
        assertEquals(contactDetails.getId(), result.getId());
        assertEquals(contactDetails.getEmail(), result.getEmail());
        assertEquals(contactDetails.getTelegramId(), result.getTelegramId());
    }

    @Test
    public void contactDetailsServiceImplUpdateContactDetails() {
        // given
        when(contactDetailsRepository.findById(contactDetailsDto.getId())).thenReturn(Optional.of(contactDetails));
        when(contactDetailsRepository.save(contactDetails)).thenReturn(contactDetails);

        // when
        ContactDetailsDto result = service.update(contactDetailsDto);

        // then
        assertEquals(contactDetails.getId(), result.getId());
        assertEquals(contactDetails.getEmail(), result.getEmail());
        assertEquals(contactDetails.getTelegramId(), result.getTelegramId());
    }

    @Test
    public void contactDetailsServiceDeleteContactDetails() {
        // when
        service.delete(contactDetailsId);

        // then
        verify(contactDetailsRepository, times(1)).deleteById(contactDetailsId);
    }

}
