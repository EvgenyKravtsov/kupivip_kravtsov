package com.kupivipkravtsov.data;

import com.kupivipkravtsov.domain.entity.FavoriteTranslation;
import com.kupivipkravtsov.domain.entity.Translation;

import io.reactivex.Observable;

public interface FavoriteTranslationsStorage {

    void add(FavoriteTranslation favoriteTranslation);

    Observable<String> get(Translation translation);

    Observable<FavoriteTranslation> getAll();

    void remove(FavoriteTranslation favoriteTranslation);
}
