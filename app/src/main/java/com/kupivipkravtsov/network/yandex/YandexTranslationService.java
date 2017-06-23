package com.kupivipkravtsov.network.yandex;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kupivipkravtsov.data.NetworkTranslationCache;
import com.kupivipkravtsov.data.NetworkTranslationService;
import com.kupivipkravtsov.domain.entity.Language;
import com.kupivipkravtsov.domain.entity.Translation;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public final class YandexTranslationService implements NetworkTranslationService {

    private static final String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";
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
    public Observable<String> translate(Translation translation) {
        return Observable.create(emitter -> {
            String url = BASE_URL + "translate?key=" + API_KEY
                    + "&text=" + translation.getTextToTranslate()
                    + "&lang=" + translation.getLanguageCode();

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest translationRequest = new StringRequest(
                    Request.Method.GET, url,
                    response -> {
                        try {
                            String textTranslated = YandexJsonParser.parseTranslationResponse(response);
                            emitter.onNext(textTranslated);
                            networkTranslationCache.add(translation, textTranslated);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            emitter.onNext(translation.getTextToTranslate());
                        }
                    },
                    error -> emitter.onNext(translation.getTextToTranslate())
            );

            requestQueue.add(translationRequest);
        });
    }

    @Override
    public Observable<List<Language>> requestSupportedLanguages() {
        return Observable.create(emitter -> {
            String url = BASE_URL + "getLangs?key=" + API_KEY + "&ui=ru";

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest translationRequest = new StringRequest(
                    Request.Method.GET, url,
                    response -> {
                        try {
                            List<Language> supportedLanguages = YandexJsonParser.parseSupportedLanguagesResponse(response);
                            emitter.onNext(supportedLanguages);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            emitter.onNext(new ArrayList<>());
                        }
                    },
                    error -> emitter.onNext(new ArrayList<>())
            );

            requestQueue.add(translationRequest);
        });
    }
}
