package br.net.silva.daniel.shared.business.utils;

public interface GenericErrorUtils {

    static IllegalArgumentException executeException(String messageError) {
        return new IllegalArgumentException(messageError);
    }

    static IllegalArgumentException executeExceptionNotPermissionOperation() {
        return executeException("Not Permission for this operation");
    }

    static RuntimeException executeErrorAtExecuteBuilder(Exception e) {
        return new RuntimeException(e);
    }
}
