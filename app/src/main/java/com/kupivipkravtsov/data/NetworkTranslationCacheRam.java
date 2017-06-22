package com.kupivipkravtsov.data;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public final class NetworkTranslationCacheRam implements NetworkTranslationCache {

    private static final String TAG = NetworkTranslationCacheRam.class.getSimpleName();

    private Map<String, String> cache = new HashMap<>();

    //// NETWORK REPOSITORY CACHE

    @Override
    public Observable<String> get(String textToTranslate) {
        return Observable.fromIterable(cache.entrySet())
                .filter(entry -> entry.getKey().equals(textToTranslate))
                .map(Map.Entry::getValue);
    }

    @Override
    public void add(String textToTranslate, String textTranslated) {
        cache.put(textToTranslate, textTranslated);
    }
}