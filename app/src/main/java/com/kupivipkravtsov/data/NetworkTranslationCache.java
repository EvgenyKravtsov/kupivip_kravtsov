package com.kupivipkravtsov.data;

import com.kupivipkravtsov.domain.entity.Translation;

import io.reactivex.Observable;

public interface NetworkTranslationCache {

    Observable<String> get(Translation translation);

    void add(Translation translation, String textTranslated);
}
