package com.kupivipkravtsov.domain.interactor;


import com.kupivipkravtsov.domain.TranslationRepository;
import com.kupivipkravtsov.domain.entity.FavoriteTranslation;

import io.reactivex.Single;

public final class RemoveFavoriteTranslationInteractor {

    private final TranslationRepository translationRepository;

    ////

    public RemoveFavoriteTranslationInteractor(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    ////

    public void execute(FavoriteTranslation favoriteTranslation) {
        translationRepository.removeFavoriteTranslation(favoriteTranslation);
    }
}
