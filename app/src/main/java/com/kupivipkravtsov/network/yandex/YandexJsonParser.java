package com.kupivipkravtsov.network.yandex;

import org.json.JSONException;
import org.json.JSONObject;

final class YandexJsonParser {

    static String parseTranslationResponse(String response) throws JSONException {
        JSONObject responseJson = new JSONObject(response);
        return responseJson.getString("text");
    }
}
