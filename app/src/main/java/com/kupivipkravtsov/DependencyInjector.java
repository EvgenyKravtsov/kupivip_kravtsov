package com.kupivipkravtsov;

import com.kupivipkravtsov.data.FavoriteTranslationStorage;
import com.kupivipkravtsov.data.NetworkTranslationCache;
import com.kupivipkravtsov.data.NetworkTranslationCacheRam;
import com.kupivipkravtsov.data.TranslationRepositoryMain;
import com.kupivipkravtsov.domain.TranslationRepository;
import com.kupivipkravtsov.network.yandex.YandexTranslationService;
import com.kupivipkravtsov.persistense.GreenDao;

public final class DependencyInjector {

    private static NetworkTranslationCache networkTranslationCache;
    private static FavoriteTranslationStorage favoriteTranslationStorage;

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

    private static FavoriteTranslationStorage provideFavoriteTranslationStorage() {
        if (favoriteTranslationStorage == null) favoriteTranslationStorage = new GreenDao(App.getContext());
        return favoriteTranslationStorage;
    }
}