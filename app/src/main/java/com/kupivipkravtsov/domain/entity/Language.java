package com.kupivipkravtsov.domain.entity;


public final class Language {

    private final String code;
    private final String description;

    ////

    public Language(String code, String description) {
        this.code = code;
        this.description = description;
    }

    ////

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
