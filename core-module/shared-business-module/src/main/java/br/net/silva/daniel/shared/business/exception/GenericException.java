package br.net.silva.daniel.shared.business.exception;

public class GenericException extends Exception {
    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }
}
