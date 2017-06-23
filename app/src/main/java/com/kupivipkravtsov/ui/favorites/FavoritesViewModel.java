package com.kupivipkravtsov.ui.favorites;

import com.kupivipkravtsov.domain.entity.FavoriteTranslation;
import com.kupivipkravtsov.domain.interactor.GetFavoritesTranslationsInteractor;
import com.kupivipkravtsov.domain.interactor.RemoveFavoriteTranslationInteractor;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;

public final class FavoritesViewModel {

    private final BehaviorSubject<List<FavoriteTranslation>> favoriteTranslations = BehaviorSubject.create();

    private final GetFavoritesTranslationsInteractor getFavoritesTranslationsInteractor;
    private final RemoveFavoriteTranslationInteractor removeFavoriteTranslationInteractor;

    ////

    public FavoritesViewModel(GetFavoritesTranslationsInteractor getFavoritesTranslationsInteractor,
                              RemoveFavoriteTranslationInteractor removeFavoriteTranslationInteractor) {
        this.getFavoritesTranslationsInteractor = getFavoritesTranslationsInteractor;
        this.removeFavoriteTranslationInteractor = removeFavoriteTranslationInteractor;
    }

    ////

    BehaviorSubject<List<FavoriteTranslation>> getFavoriteTranslations() {
        return favoriteTranslations;
    }

    void onViewVisibleToUser() {
        getFavoritesTranslationsInteractor.execute().toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoriteTranslations::onNext);
    }

    void onRemoveFavoriteTranslation(FavoriteTranslation favoriteTranslation) {
        removeFavoriteTranslationInteractor.execute(favoriteTranslation);
    }
}
