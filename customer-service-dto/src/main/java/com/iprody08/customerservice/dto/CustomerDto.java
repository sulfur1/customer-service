package com.iprody08.customerservice.dto;

import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "^(.+)@(\\S+)$", message = "email must match pattern user@domain.com")
    private String email;

    @Pattern(regexp = ".*\\B@(?=\\w{5,32}\\b)[a-zA-Z0-9]+(?:_[a-zA-Z0-9]+)*.*")
    private String telegramId;

    private Instant createdCustomerAt;

    private Instant updatedCustomerAt;

    private Instant createdContactDetailsAt;

    private Instant updatedContactDetailsAt;
}
