package com.kupivipkravtsov.domain.interactor;

import com.kupivipkravtsov.DependencyInjector;

public final class InteractorFactory {

    public static TranslateInteractor provideTranslateInteractor() {
        return new TranslateInteractor(DependencyInjector.provideTranslationRepository());
    }

    public static AddTranslationToFavoritesInteractor provideAddTranslationToFavoritesInteractor() {
        return new AddTranslationToFavoritesInteractor(DependencyInjector.provideTranslationRepository());
    }

    public static GetFavoritesTranslationsInteractor provideGetFavoriteTranslationsInteractor() {
        return new GetFavoritesTranslationsInteractor(DependencyInjector.provideTranslationRepository());
    }
}
