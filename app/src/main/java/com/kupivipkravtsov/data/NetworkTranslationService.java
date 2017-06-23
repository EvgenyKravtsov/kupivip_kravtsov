package com.kupivipkravtsov.data;

import java.util.List;

import io.reactivex.Observable;

public interface NetworkTranslationService {

    Observable<String> translate(String textToTranslate);

    Observable<List<String>> requestSupportedLanguages();
}
