package com.iprody08.customerservice.for_tests;

import com.iprody08.customerservice.dto.ContactDetailsDto;
import com.iprody08.customerservice.dto.CustomerDto;
import com.iprody08.customerservice.entities.ContactDetails;
import com.iprody08.customerservice.entities.Country;
import com.iprody08.customerservice.entities.Customer;

import java.time.Instant;

public final class CustomerServiceConstants {

    public static final String EMAIL_FIRST = "firstEmail@gmail.com";

    public static final String EMAIL_SECOND = "secondEmail@gmail.com";

    public static final String TELEGRAM_ID_FIRST = "@firstTelegramId";

    public static final String TELEGRAM_ID_SECOND = "@secondTelegramId";

    public static final String FIRST_CUSTOMER_NAME = "John";

    public static final String FIRST_CUSTOMER_SURNAME = "Doe";

    public static final String SECOND_CUSTOMER_NAME = "Janis";

    public static final String SECOND_CUSTOMER_SURNAME = "Ozols";

    public static final String US_COUNTRY_NAME = "United States";

    public static final String LV_COUNTRY_NAME = "Latvia";

    public static final Country US = new Country(
            1L,
            "USA",
            US_COUNTRY_NAME,
            null,
            Instant.now(),
            Instant.now()
    );

    public static final Country LV = new Country(
            2L,
            "LVA",
            LV_COUNTRY_NAME,
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

    public static final Customer FIRST_CUSTOMER = new Customer(
            1L,
            FIRST_CUSTOMER_NAME,
            FIRST_CUSTOMER_SURNAME,
            US,
            CONTACT_DETAILS_FIRST,
            Instant.now(),
            Instant.now()
    );

    public static final Customer SECOND_CUSTOMER = new Customer(
            2L,
            SECOND_CUSTOMER_NAME,
            SECOND_CUSTOMER_SURNAME,
            LV,
            CONTACT_DETAILS_SECOND,
            Instant.now(),
            Instant.now()
    );

    public static final CustomerDto FIRST_CUSTOMER_DTO = new CustomerDto(
            1L,
            FIRST_CUSTOMER_NAME,
            FIRST_CUSTOMER_SURNAME,
            1L,
            US_COUNTRY_NAME,
            EMAIL_FIRST,
            TELEGRAM_ID_FIRST,
            Instant.now(),
            Instant.now(),
            Instant.now(),
            Instant.now()
    );

    public static final CustomerDto SECOND_CUSTOMER_DTO = new CustomerDto(
            2L,
            SECOND_CUSTOMER_NAME,
            SECOND_CUSTOMER_SURNAME,
            2L,
            LV_COUNTRY_NAME,
            EMAIL_SECOND,
            TELEGRAM_ID_SECOND,
            Instant.now(),
            Instant.now(),
            Instant.now(),
            Instant.now()
    );

}
