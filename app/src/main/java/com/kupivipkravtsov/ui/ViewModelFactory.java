package com.kupivipkravtsov.ui;

import com.kupivipkravtsov.domain.interactor.InteractorFactory;
import com.kupivipkravtsov.ui.favorites.FavoritesViewModel;
import com.kupivipkravtsov.ui.translation.TranslationViewModel;

public final class ViewModelFactory {

    private static TranslationViewModel translationViewModel;
    private static FavoritesViewModel favoritesViewModel;

    ////

    public static TranslationViewModel provideTranslationViewModel() {
        if (translationViewModel == null) {
            translationViewModel = new TranslationViewModel(
                    InteractorFactory.provideTranslateInteractor(),
                    InteractorFactory.provideAddTranslationToFavoritesInteractor(),
                    InteractorFactory.provideGetSupportedLanguagesInteractor());
        }
        return translationViewModel;
    }

    public static FavoritesViewModel provideFavoritesViewModel() {
        if (favoritesViewModel == null) {
            favoritesViewModel = new FavoritesViewModel(
                    InteractorFactory.provideGetFavoriteTranslationsInteractor(),
                    InteractorFactory.provideRemoveFavoriteTranslationInteractor());
        }
        return favoritesViewModel;
    }
}
