package com.kupivipkravtsov.domain.interactor;


import com.kupivipkravtsov.domain.TranslationRepository;
import com.kupivipkravtsov.domain.entity.Language;

import java.util.List;

import io.reactivex.Observable;

public class GetSupportedLanguagesInteractor {

    private final TranslationRepository translationRepository;

    ////

    GetSupportedLanguagesInteractor(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    ////

    public Observable<List<Language>> execute() {
        return translationRepository.querySupportedLanguages();
    }
}
