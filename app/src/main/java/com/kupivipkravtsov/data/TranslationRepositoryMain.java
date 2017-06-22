package com.kupivipkravtsov.data;

import com.kupivipkravtsov.domain.TranslationRepository;
import com.kupivipkravtsov.domain.entity.FavoriteTranslation;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public final class TranslationRepositoryMain implements TranslationRepository {

    private static final String TAG = TranslationRepositoryMain.class.getSimpleName();

    private final NetworkTranslationService networkTranslationService;
    private final NetworkTranslationCache networkTranslationCache;
    private final FavoriteTranslationStorage favoriteTranslationStorage;

    ////

    public TranslationRepositoryMain(NetworkTranslationService networkTranslationService,
                                     NetworkTranslationCache networkTranslationCache,
                                     FavoriteTranslationStorage favoriteTranslationStorage) {
        this.networkTranslationService = networkTranslationService;
        this.networkTranslationCache = networkTranslationCache;
        this.favoriteTranslationStorage = favoriteTranslationStorage;
    }

    //// TRANSLATION REPOSITORY

    @Override
    public Single<String> queryTranslation(String textToTranslate) {
        return Observable.concat(
                networkTranslationService.translate(textToTranslate),
                networkTranslationCache.get(textToTranslate),
                favoriteTranslationStorage.get(textToTranslate))
                .first(textToTranslate)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public void addFavoriteTranslation(FavoriteTranslation favoriteTranslation) {
        Single.just(true).doOnSuccess( value -> favoriteTranslationStorage.add(favoriteTranslation))
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    @Override
    public Observable<FavoriteTranslation> queryFavoriteTranslations() {
        return favoriteTranslationStorage.getFavoriteTranslations().subscribeOn(Schedulers.io());
    }
}
