package br.net.silva.daniel.shared.business.exception;

public class DomainGenericException extends Exception {
    public DomainGenericException(String message) {
        super(message);
    }

    public DomainGenericException(String message, Throwable cause) {
        super(message, cause);
    }
}
