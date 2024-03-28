package com.iprody08.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.Instant;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {

    private Long id;

    private String name;

    private String countryCode;

    private Instant createdAt;

    private Instant updatedAt;

}
