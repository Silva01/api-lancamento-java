package br.net.silva.daniel.utils;

import java.util.Map;
import java.util.Objects;

public abstract class ExtractMapUtils {

    private ExtractMapUtils() {
    }

    public static <T> T extractMapValue(Map<String, String> map, String key, Class<T> clazz) {
        if (Objects.isNull(map) || !map.containsKey(key)) {
            return null;
        }
        return clazz.cast(map.get(key));
    }
}
