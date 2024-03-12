package com.iprody08.customerservice.entities.enums;

/*
* ISO 3166-1 alpha-3 https://en.wikipedia.org/wiki/ISO_3166-1_alpha-3
* */
public enum CountryCode {
    RUS("Russian Federation"),
    LVA("Latvia"),
    IND("India"),
    CZE("Czechia");

    private final String name;

    CountryCode(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
