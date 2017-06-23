package com.kupivipkravtsov.network.yandex;

import com.kupivipkravtsov.domain.entity.Language;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

final class YandexJsonParser {

    static String parseTranslationResponse(String response) throws JSONException {
        JSONObject responseJson = new JSONObject(response);
        return responseJson.getString("text");
    }

    static List<Language> parseSupportedLanguagesResponse(String response) throws JSONException {
        List<Language> supportedLanguages = new ArrayList<>();
        JSONObject responseJson = new JSONObject(response);
        JSONObject languages = responseJson.getJSONObject("langs");

        for (int i = 0; i < languages.names().length(); i++) {
            String key = languages.names().getString(i);
            supportedLanguages.add(new Language(key, languages.getString(key)));
        }

        return supportedLanguages;
    }
}
