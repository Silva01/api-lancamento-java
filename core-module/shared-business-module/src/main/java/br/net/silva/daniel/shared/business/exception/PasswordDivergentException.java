package br.net.silva.daniel.shared.business.exception;

public class PasswordDivergentException extends RuntimeException {
    public PasswordDivergentException(String message) {
        super(message);
    }
}
