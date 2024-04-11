package com.iprody08.customerservice.for_tests;

import com.iprody08.customerservice.entities.Country;
import java.time.Instant;

public final class CustomerServiceConstants {

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

}
