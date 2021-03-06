package com.kupivipkravtsov.domain.entity;

public final class FavoriteTranslation {

    private final String languageCode;
    private final String textToTranslate;
    private final String textTranslated;

    ////

    public FavoriteTranslation(String languageCode, String textToTranslate, String textTranslated) {
        this.languageCode = languageCode;
        this.textToTranslate = textToTranslate;
        this.textTranslated = textTranslated;
    }

    ////

    public String getLanguageCode() {
        return languageCode;
    }

    public String getTextToTranslate() {
        return textToTranslate;
    }

    public String getTextTranslated() {
        return textTranslated;
    }
}
