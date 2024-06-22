package br.net.silva.daniel.shared.application.utils;

import br.net.silva.daniel.shared.application.adapter.LocalDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDate;
import java.util.Map;

public class ConverterUtils {
    private static Gson gson;
    
    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
    }
    
    public static String convertObjectToJson(Object object) {
        return gson.toJson(object);
    }

    public static Map<String, Object> convertJsonToMap(String json) {
        return dealWithValuesDoubleToInteger(gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType()));
    }

    public static Map<String, String> convertJsonToInputMap(String json) {
        return gson.fromJson(json, new TypeToken<Map<String, String>>() {}.getType());
    }

    private static Map<String, Object> dealWithValuesDoubleToInteger(Map<String, Object> map) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof Double) {
                Double value = (Double) entry.getValue();
                if (value % 1 == 0) {
                    entry.setValue(value.longValue());
                }
            }
        }

        return map;
    }
}
