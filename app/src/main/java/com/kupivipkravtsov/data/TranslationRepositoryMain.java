package com.kupivipkravtsov.data;

import com.kupivipkravtsov.domain.TranslationRepository;
import com.kupivipkravtsov.domain.entity.FavoriteTranslation;
import com.kupivipkravtsov.domain.entity.Translation;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public final class TranslationRepositoryMain implements TranslationRepository {

    private static final String TAG = TranslationRepositoryMain.class.getSimpleName();

    private final NetworkTranslationService networkTranslationService;
    private final NetworkTranslationCache networkTranslationCache;
    private final FavoriteTranslationsStorage favoriteTranslationsStorage;

    ////

    public TranslationRepositoryMain(NetworkTranslationService networkTranslationService,
                                     NetworkTranslationCache networkTranslationCache,
                                     FavoriteTranslationsStorage favoriteTranslationsStorage) {
        this.networkTranslationService = networkTranslationService;
        this.networkTranslationCache = networkTranslationCache;
        this.favoriteTranslationsStorage = favoriteTranslationsStorage;
    }

    //// TRANSLATION REPOSITORY

    @Override
    public Single<String> queryTranslation(Translation translation) {
        return Observable.concat(
                networkTranslationService.translate(translation),
                networkTranslationCache.get(translation),
                favoriteTranslationsStorage.get(translation))
                .first(translation.getTextToTranslate())
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void addFavoriteTranslation(FavoriteTranslation favoriteTranslation) {
        Single.just(true).doOnSuccess(value -> favoriteTranslationsStorage.add(favoriteTranslation))
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    @Override
    public Observable<FavoriteTranslation> queryFavoriteTranslations() {
        return favoriteTranslationsStorage.getAll().subscribeOn(Schedulers.io());
    }

    @Override
    public void removeFavoriteTranslation(FavoriteTranslation favoriteTranslation) {
        Single.just(true).doOnSuccess( value -> favoriteTranslationsStorage.remove(favoriteTranslation))
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    @Override
    public Observable<List<String>> querySupportedLanguages() {
        return networkTranslationService.requestSupportedLanguages().subscribeOn(Schedulers.io());
    }
}
