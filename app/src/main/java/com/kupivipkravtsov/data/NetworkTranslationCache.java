package com.kupivipkravtsov.data;

import io.reactivex.Observable;

public interface NetworkTranslationCache {

    Observable<String> get(String textToTranslate);

    void add(String textToTranslate, String textTranslated);
}
