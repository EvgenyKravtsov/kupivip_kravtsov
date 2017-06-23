package com.kupivipkravtsov.persistense;

import com.kupivipkravtsov.data.NetworkTranslationCache;
import com.kupivipkravtsov.domain.entity.Translation;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public final class NetworkTranslationCacheRam implements NetworkTranslationCache {

    private Map<Translation, String> cache = new HashMap<>();

    //// NETWORK REPOSITORY CACHE

    @Override
    public Observable<String> get(Translation translation) {
        return Observable.fromIterable(cache.entrySet())
                .filter(entry -> entry.getKey().equals(translation))
                .map(Map.Entry::getValue);
    }

    @Override
    public void add(Translation translation, String textTranslated) {
        cache.put(translation, textTranslated);
    }
}