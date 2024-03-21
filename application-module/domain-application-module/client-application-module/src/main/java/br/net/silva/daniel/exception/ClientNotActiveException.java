package br.net.silva.daniel.exception;

import br.net.silva.daniel.shared.application.exception.GenericException;

public class ClientNotActiveException extends GenericException {
    public ClientNotActiveException(String message) {
        super(message);
    }
}
