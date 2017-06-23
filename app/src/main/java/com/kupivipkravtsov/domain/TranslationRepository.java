package com.kupivipkravtsov.domain;

import com.kupivipkravtsov.domain.entity.FavoriteTranslation;
import com.kupivipkravtsov.domain.entity.Language;
import com.kupivipkravtsov.domain.entity.Translation;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface TranslationRepository {

    Single<String> queryTranslation(Translation translation);

    void addFavoriteTranslation(FavoriteTranslation favoriteTranslation);

    Observable<FavoriteTranslation> queryFavoriteTranslations();

    void removeFavoriteTranslation(FavoriteTranslation favoriteTranslation);

    Observable<List<Language>> querySupportedLanguages();
}
