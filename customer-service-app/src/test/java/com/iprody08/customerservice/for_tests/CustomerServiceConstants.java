package com.iprody08.customerservice.for_tests;

import com.iprody08.customerservice.dto.ContactDetailsDto;
import com.iprody08.customerservice.entities.ContactDetails;
import com.iprody08.customerservice.entities.Country;

import java.time.Instant;

public final class CustomerServiceConstants {

    public static final String EMAIL_FIRST = "firstEmail@gmail.com";

    public static final String EMAIL_SECOND = "secondEmail@gmail.com";

    public static final String TELEGRAM_ID_FIRST = "@firstTelegramId";

    public static final String TELEGRAM_ID_SECOND = "@secondTelegramId";

    public static final Country US = new Country(
            1L,
            "USA",
            "United States",
            null,
            Instant.now(),
            Instant.now()
    );

    public static final Country LV = new Country(
            2L,
            "LVA",
            "Latvia",
            null,
            Instant.now(),
            Instant.now()
    );

    public static final ContactDetails CONTACT_DETAILS_FIRST = new ContactDetails(
            1L,
            null,
            EMAIL_FIRST,
            TELEGRAM_ID_FIRST,
            Instant.now(),
            Instant.now()
    );

    public static final ContactDetails CONTACT_DETAILS_SECOND = new ContactDetails(
            2L,
            null,
            EMAIL_SECOND,
            TELEGRAM_ID_SECOND,
            Instant.now(),
            Instant.now()
    );

    public static final ContactDetailsDto FIRST_CONTACT_DETAILS_DTO = new ContactDetailsDto(
            1L,
            1L,
            EMAIL_FIRST,
            TELEGRAM_ID_FIRST,
            Instant.now(),
            Instant.now()
    );

    public static final ContactDetailsDto SECOND_CONTACT_DETAILS_DTO = new ContactDetailsDto(
            2L,
            2L,
            EMAIL_SECOND,
            TELEGRAM_ID_SECOND,
            Instant.now(),
            Instant.now()
    );

}
