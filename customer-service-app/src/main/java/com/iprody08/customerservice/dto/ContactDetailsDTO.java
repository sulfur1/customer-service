package com.iprody08.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactDetailsDTO {

    private Long id;

    private Long customerId;

    private String email;

    private String telegramId;

    private Instant createdAt;

    private Instant updatedAt;

}
