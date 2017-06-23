package com.kupivipkravtsov.network.yandex;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class YandexJsonParser {

    private static final String TAG = YandexJsonParser.class.getSimpleName();

    static String parseTranslationResponse(String response) throws JSONException {
        JSONObject responseJson = new JSONObject(response);
        return responseJson.getString("text");
    }

    static List<String> parseSupportedLanguagesResponse(String response) throws JSONException {
        List<String> supportedLanguages = new ArrayList<>();
        JSONObject responseJson = new JSONObject(response);
        JSONObject languages = responseJson.getJSONObject("langs");

        for (int i = 0; i < languages.names().length(); i++) {
            String key = languages.names().getString(i);
            supportedLanguages.add(languages.getString(key));
        }

        return supportedLanguages;
    }
}
