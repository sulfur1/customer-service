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
public class ContactDetailsDto {

    private Long id;

    private Long customerId;

    @Pattern(regexp = "^(.+)@(\\S+)$", message = "email must match pattern user@domain.com")
    private String email;

    @Pattern(regexp = "^@[a-zA-Z0-9]+$")
    private String telegramId;

    private Instant createdAt;

    private Instant updatedAt;

}
