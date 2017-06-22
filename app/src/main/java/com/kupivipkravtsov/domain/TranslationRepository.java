package com.kupivipkravtsov.domain;

import com.kupivipkravtsov.domain.entity.FavoriteTranslation;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface TranslationRepository {

    Single<String> queryTranslation(String textToTranslate);

    void addFavoriteTranslation(FavoriteTranslation favoriteTranslation);

    Observable<FavoriteTranslation> queryFavoriteTranslations();
}
