package com.kupivipkravtsov.domain.interactor;


import com.kupivipkravtsov.domain.TranslationRepository;

import java.util.List;

import io.reactivex.Observable;

public class GetSupportedLanguagesInteractor {

    private final TranslationRepository translationRepository;

    ////

    GetSupportedLanguagesInteractor(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    ////

    public Observable<List<String>> execute() {
        return translationRepository.querySupportedLanguages();
    }
}
