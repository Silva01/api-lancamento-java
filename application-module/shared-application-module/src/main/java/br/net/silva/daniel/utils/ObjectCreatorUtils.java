package br.net.silva.daniel.utils;

public abstract class ObjectCreatorUtils {

    private ObjectCreatorUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> T createObjectWithEmptyConstructor(Class<T> clazz) throws Exception {
        return clazz.getDeclaredConstructor().newInstance();
    }
}
