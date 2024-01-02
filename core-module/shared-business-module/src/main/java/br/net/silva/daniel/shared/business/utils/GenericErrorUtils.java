package br.net.silva.daniel.shared.business.utils;

public abstract class GenericErrorUtils {

    private GenericErrorUtils() {
        throw executeException("Utility class");
    }

    public static IllegalArgumentException executeException(String messageError) {
        return new IllegalArgumentException(messageError);
    }
}
