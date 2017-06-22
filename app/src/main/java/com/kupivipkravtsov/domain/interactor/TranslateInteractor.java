package com.kupivipkravtsov.domain.interactor;

import com.kupivipkravtsov.domain.TranslationRepository;

import io.reactivex.Observable;
import io.reactivex.Single;

public final class TranslateInteractor {

    private final TranslationRepository translationRepository;

    ////

    TranslateInteractor(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    ////

    public Single<String> execute(String textToTranslate) {
        return translationRepository.queryTranslation(textToTranslate);
    }
}
