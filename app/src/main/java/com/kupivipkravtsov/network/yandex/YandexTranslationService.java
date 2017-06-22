package com.kupivipkravtsov.network.yandex;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kupivipkravtsov.data.NetworkTranslationCache;
import com.kupivipkravtsov.data.NetworkTranslationService;

import org.json.JSONException;

import io.reactivex.Observable;

public final class YandexTranslationService implements NetworkTranslationService {

    private static final String TAG = YandexTranslationService.class.getSimpleName();

    private static final String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private static final String API_KEY = "trnsl.1.1.20170621T200152Z.73ed51111988759d.82bd740904713f4c70221af6be509762221c937a";

    private final Context context;
    private final NetworkTranslationCache networkTranslationCache;

    ////

    public YandexTranslationService(Context context, NetworkTranslationCache networkTranslationCache) {
        this.context = context;
        this.networkTranslationCache = networkTranslationCache;
    }

    //// NETWORK TRANSLATION SERVICE

    @Override
    public Observable<String> translate(String textToTranslate) {
        return Observable.create(emitter -> {
            String url = BASE_URL + "?key=" + API_KEY
                    + "&text=" + textToTranslate
                    + "&lang=ru";

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest translationRequest = new StringRequest(
                    Request.Method.GET, url,
                    response -> {
                        try {
                            String textTranslated = YandexJsonParser.parseTranslationResponse(response);
                            emitter.onNext(textTranslated);
                            networkTranslationCache.add(textToTranslate, textTranslated);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            emitter.onNext(textToTranslate);
                        }
                    },
                    error -> emitter.onNext(textToTranslate)
            );

            requestQueue.add(translationRequest);
        });
    }
}
