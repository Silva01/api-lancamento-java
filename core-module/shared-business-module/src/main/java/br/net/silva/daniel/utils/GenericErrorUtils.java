package br.net.silva.daniel.utils;

public abstract class GenericErrorUtils {

    public static void executeException(String messageError) {
        throw new IllegalArgumentException(messageError);
    }
}
