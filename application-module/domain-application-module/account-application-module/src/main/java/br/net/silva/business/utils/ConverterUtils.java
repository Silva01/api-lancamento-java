package br.net.silva.business.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public class ConverterUtils {
    private static Gson gson;
    
    static {
        gson = new Gson();
    }
    
    public static String convertObjectToJson(Object object) {
        return gson.toJson(object);
    }

    public static Map<String, Object> convertJsonToMap(String json) {
        return gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
    }
}
