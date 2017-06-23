package com.kupivipkravtsov;

import com.kupivipkravtsov.data.FavoriteTranslationsStorage;
import com.kupivipkravtsov.data.NetworkTranslationCache;
import com.kupivipkravtsov.persistense.NetworkTranslationCacheRam;
import com.kupivipkravtsov.data.TranslationRepositoryMain;
import com.kupivipkravtsov.domain.TranslationRepository;
import com.kupivipkravtsov.network.yandex.YandexTranslationService;
import com.kupivipkravtsov.persistense.SQLite;

public final class DependencyInjector {

    private static NetworkTranslationCache networkTranslationCache;
    private static FavoriteTranslationsStorage favoriteTranslationsStorage;

    ////

    public static TranslationRepository provideTranslationRepository() {
        return new TranslationRepositoryMain(
                new YandexTranslationService(App.getContext(), provideNetworkTranslationCache()),
                provideNetworkTranslationCache(),
                provideFavoriteTranslationStorage());
    }

    ////

    private static NetworkTranslationCache provideNetworkTranslationCache() {
        if (networkTranslationCache == null) networkTranslationCache = new NetworkTranslationCacheRam();
        return networkTranslationCache;
    }

    private static FavoriteTranslationsStorage provideFavoriteTranslationStorage() {
        if (favoriteTranslationsStorage == null) favoriteTranslationsStorage = SQLite.getInstance(App.getContext());
        return favoriteTranslationsStorage;
    }
}