package br.net.silva.daniel.exceptions;

public class ClientNotActiveException extends RuntimeException {
    public ClientNotActiveException(String message) {
        super(message);
    }
}
