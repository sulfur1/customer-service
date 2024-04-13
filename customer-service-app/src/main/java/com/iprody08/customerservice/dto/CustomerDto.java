package com.iprody08.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Long id;

    private String name;

    private String surname;

    private Long countryId;

    private String countryName;

    private String email;

    private String telegramId;

    private Instant createdCustomerAt;

    private Instant updatedCustomerAt;

    private Instant createdContactDetailsAt;

    private Instant updatedContactDetailsAt;

    private ContactDetailsDto contactDetails;
}
