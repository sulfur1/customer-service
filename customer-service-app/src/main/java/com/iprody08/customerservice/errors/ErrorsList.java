package com.iprody08.customerservice.errors;

import lombok.Getter;

import java.util.Map;

@Getter
public class ErrorsList {

    private final Map<String, String> errorMessages;

    public ErrorsList() {
        errorMessages = Map.of(
                "ERROR_CUSTOMER_EXISTS_MESSAGE", "Customer id already exists",
                "ERROR_CUSTOMER_NOT_FOUND_MESSAGE", "Customer id not found",
                "ERROR_COUNTRY_NOT_FOUND_MESSAGE", "Country id not found",
                "ERROR_CONTACTS_DETAILS_MESSAGE", "Contacts details have errors",
                "ERROR_CONTACTS_DETAILS_EXISTS_MESSAGE", "Contact details with id %d already exists",
                "ERROR_CONTACTS_DETAILS_NOT_FOUND_MESSAGE", "Contact details with id %d not found"
        );
    }

}
