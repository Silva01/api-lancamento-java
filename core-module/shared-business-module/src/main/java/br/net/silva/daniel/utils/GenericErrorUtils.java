package br.net.silva.daniel.utils;

public abstract class GenericErrorUtils {

    public static IllegalArgumentException executeException(String messageError) {
        return new IllegalArgumentException(messageError);
    }
}
