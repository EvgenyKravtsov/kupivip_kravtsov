package com.kupivipkravtsov.domain.entity;


public final class Translation {

    private final String languageCode;
    private final String textToTranslate;

    ////

    public Translation(String languageCode, String textToTranslate) {
        this.languageCode = languageCode;
        this.textToTranslate = textToTranslate;
    }

    ////

    public String getLanguageCode() {
        return languageCode;
    }

    public String getTextToTranslate() {
        return textToTranslate;
    }
}
