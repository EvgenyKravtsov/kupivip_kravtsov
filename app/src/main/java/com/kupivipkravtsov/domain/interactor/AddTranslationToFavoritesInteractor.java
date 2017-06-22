package com.kupivipkravtsov.domain.interactor;

import com.kupivipkravtsov.domain.TranslationRepository;
import com.kupivipkravtsov.domain.entity.FavoriteTranslation;

public final class AddTranslationToFavoritesInteractor {

    private final TranslationRepository translationRepository;

    ////

    public AddTranslationToFavoritesInteractor(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    ////

    public void execute(FavoriteTranslation favoriteTranslation) {
        translationRepository.addFavoriteTranslation(favoriteTranslation);
    }
}
