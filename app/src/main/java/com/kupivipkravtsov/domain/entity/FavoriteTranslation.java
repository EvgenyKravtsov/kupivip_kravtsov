package com.kupivipkravtsov.domain.entity;

public final class FavoriteTranslation {

    private final String textToTranslate;
    private final String textTranslated;

    ////

    public FavoriteTranslation(String textToTranslate, String textTranslated) {
        this.textToTranslate = textToTranslate;
        this.textTranslated = textTranslated;
    }

    ////

    public String getTextToTranslate() {
        return textToTranslate;
    }

    public String getTextTranslated() {
        return textTranslated;
    }
}
