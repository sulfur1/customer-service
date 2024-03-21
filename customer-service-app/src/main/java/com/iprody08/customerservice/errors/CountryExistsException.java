package com.iprody08.customerservice.errors;

public class CountryExistsException extends RuntimeException {
    public CountryExistsException(String message) {
        super(message);
    }
}
