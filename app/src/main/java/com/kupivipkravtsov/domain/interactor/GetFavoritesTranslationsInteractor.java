package com.kupivipkravtsov.domain.interactor;

import com.kupivipkravtsov.domain.TranslationRepository;
import com.kupivipkravtsov.domain.entity.FavoriteTranslation;

import io.reactivex.Observable;

public final class GetFavoritesTranslationsInteractor {

    private final TranslationRepository translationRepository;

    ////

    GetFavoritesTranslationsInteractor(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    ////

    public Observable<FavoriteTranslation> execute() {
        return translationRepository.queryFavoriteTranslations();
    }
}
