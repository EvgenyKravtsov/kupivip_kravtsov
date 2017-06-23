package com.kupivipkravtsov.domain.interactor;

import com.kupivipkravtsov.domain.TranslationRepository;
import com.kupivipkravtsov.domain.entity.Translation;

import io.reactivex.Single;

public final class TranslateInteractor {

    private final TranslationRepository translationRepository;

    ////

    TranslateInteractor(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    ////

    public Single<String> execute(Translation translation) {
        return translationRepository.queryTranslation(translation);
    }
}
