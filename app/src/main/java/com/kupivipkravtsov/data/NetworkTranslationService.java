package com.kupivipkravtsov.data;

import io.reactivex.Observable;

public interface NetworkTranslationService {

    Observable<String> translate(String textToTranslate);
}
