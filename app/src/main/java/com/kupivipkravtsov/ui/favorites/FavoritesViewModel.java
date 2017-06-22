package com.kupivipkravtsov.ui.favorites;

import com.kupivipkravtsov.domain.entity.FavoriteTranslation;
import com.kupivipkravtsov.domain.interactor.GetFavoritesTranslationsInteractor;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subjects.BehaviorSubject;

public final class FavoritesViewModel {

    private final BehaviorSubject<List<FavoriteTranslation>> favoriteTranslations = BehaviorSubject.create();

    private final GetFavoritesTranslationsInteractor getFavoritesTranslationsInteractor;

    ////

    public FavoritesViewModel(GetFavoritesTranslationsInteractor getFavoritesTranslationsInteractor) {
        this.getFavoritesTranslationsInteractor = getFavoritesTranslationsInteractor;
    }

    ////

    public BehaviorSubject<List<FavoriteTranslation>> getFavoriteTranslations() {
        return favoriteTranslations;
    }

    public void onViewVisibleToUser() {
        getFavoritesTranslationsInteractor.execute().toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoriteTranslations::onNext);
    }
}
