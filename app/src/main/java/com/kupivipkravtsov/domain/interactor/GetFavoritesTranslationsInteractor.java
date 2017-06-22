package com.kupivipkravtsov.domain.interactor;

import com.kupivipkravtsov.data.FavoriteTranslationStorage;
import com.kupivipkravtsov.domain.TranslationRepository;
import com.kupivipkravtsov.domain.entity.FavoriteTranslation;

import io.reactivex.Observable;

public final class GetFavoritesTranslationsInteractor {

    private final TranslationRepository translationRepository;

    ////

    public GetFavoritesTranslationsInteractor(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    ////

    public Observable<FavoriteTranslation> execute() {
        return translationRepository.queryFavoriteTranslations();
    }
}
