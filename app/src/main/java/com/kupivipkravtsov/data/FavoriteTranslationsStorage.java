package com.kupivipkravtsov.data;

import com.kupivipkravtsov.domain.entity.FavoriteTranslation;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface FavoriteTranslationsStorage {

    void add(FavoriteTranslation favoriteTranslation);

    Observable<String> get(String languageCode, String textToTranslate);

    Observable<FavoriteTranslation> getAll();

    void remove(String languageCode, FavoriteTranslation favoriteTranslation);
}
