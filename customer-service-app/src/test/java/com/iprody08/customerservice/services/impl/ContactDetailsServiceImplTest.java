package com.iprody08.customerservice.services.impl;

import com.iprody08.customerservice.dto.ContactDetailsDto;
import com.iprody08.customerservice.dto.mapper.ContactDetailsMapper;
import com.iprody08.customerservice.entities.ContactDetails;
import com.iprody08.customerservice.for_tests.CustomerServiceConstants;
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

    @Mock
    private ContactDetailsRepository contactDetailsRepository;

    @Spy
    private ContactDetailsMapper contactDetailsMapper = Mappers.getMapper(ContactDetailsMapper.class);

    @InjectMocks
    private ContactDetailsServiceImpl service;

    @BeforeEach
    void setUp() {

        contactDetails = CustomerServiceConstants.CONTACT_DETAILS_FIRST;

        contactDetailsDto = CustomerServiceConstants.FIRST_CONTACT_DETAILS_DTO;
    }


    @Test
    void testContactDetailsServiceImplFindContactDetailsById() {
        // given
        when(contactDetailsRepository.findById(contactDetails.getId())).thenReturn(Optional.of(contactDetails));

        // when
        Optional<ContactDetailsDto> result = service.findContactsById(contactDetails.getId());

        // then
        assertTrue(result.isPresent(), "ContactDetailsDto should be present");
        assertEquals(contactDetails.getEmail(), result.get().getEmail());
        assertEquals(contactDetails.getTelegramId(), result.get().getTelegramId());
    }

    @Test
    public void testContactDetailsServiceImplFindContactDetailsByIdWhenIdDoesNotExist() {
        // given
        when(contactDetailsRepository.findById(contactDetails.getId())).thenReturn(Optional.empty());

        // when
        Optional<ContactDetailsDto> result = service.findContactsById(contactDetails.getId());

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    void testContactDetailsServiceImplFindAllContactDetails() {
        // given
        List<ContactDetails> contactDetailsList = List.of(
                CustomerServiceConstants.CONTACT_DETAILS_FIRST,
                CustomerServiceConstants.CONTACT_DETAILS_SECOND);
        when(contactDetailsRepository.findAll()).thenReturn(contactDetailsList);
        List<ContactDetailsDto> expectedDTOs = List.of(
                CustomerServiceConstants.FIRST_CONTACT_DETAILS_DTO,
                CustomerServiceConstants.SECOND_CONTACT_DETAILS_DTO);

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
    void testContactDetailsServiceImplSaveContactDetailsReturnContactDetailsDto() {
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
    public void testContactDetailsServiceImplUpdateContactDetailsReturnContactDetailsDto() {
        // given
        when(contactDetailsRepository.findById(contactDetails.getId())).thenReturn(Optional.of(contactDetails));
        when(contactDetailsRepository.save(contactDetails)).thenReturn(contactDetails);

        // when
        ContactDetailsDto result = service.update(contactDetailsDto);

        // then
        assertEquals(contactDetails.getId(), result.getId());
        assertEquals(contactDetails.getEmail(), result.getEmail());
        assertEquals(contactDetails.getTelegramId(), result.getTelegramId());
    }

    @Test
    public void testContactDetailsServiceDeleteContactDetails() {
        // when
        service.delete(contactDetails.getId());

        // then
        verify(contactDetailsRepository, times(1)).deleteById(contactDetails.getId());
    }

}
