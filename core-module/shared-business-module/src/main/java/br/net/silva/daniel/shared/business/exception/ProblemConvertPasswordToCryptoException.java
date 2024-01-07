package br.net.silva.daniel.shared.business.exception;

public class ProblemConvertPasswordToCryptoException extends RuntimeException {
    public ProblemConvertPasswordToCryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}
