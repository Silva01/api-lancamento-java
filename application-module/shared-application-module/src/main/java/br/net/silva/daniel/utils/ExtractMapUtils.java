package br.net.silva.daniel.utils;

import java.util.Map;

public abstract class ExtractMapUtils {

    private ExtractMapUtils() {
    }

    public static <T> T extractMapValue(Map<String, Object> map, String domainKey, String key, Class<T> clazz) {
        if (!validateMapKey(map, domainKey, key)) {
            return null;
        }
        var mapAccount = (Map<String, Object>) map.get(domainKey);

        return clazz.cast(mapAccount.get(key));
    }

    private static boolean validateMapKey(Map<String, Object> map, String domainKey, String key) {
        if (!map.containsKey(domainKey)) {
            return false;
        }

        var mapAccount = (Map<String, Object>) map.get(domainKey);

        return mapAccount.containsKey(key);
    }
}
