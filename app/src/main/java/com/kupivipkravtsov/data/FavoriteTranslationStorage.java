package com.kupivipkravtsov.data;

import com.kupivipkravtsov.domain.entity.FavoriteTranslation;

import io.reactivex.Observable;

public interface FavoriteTranslationStorage {

    void add(FavoriteTranslation favoriteTranslation);

    Observable<String> get(String textToTranslate);

    Observable<FavoriteTranslation> getFavoriteTranslations();
}
