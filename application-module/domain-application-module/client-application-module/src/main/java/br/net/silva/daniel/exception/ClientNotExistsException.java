package br.net.silva.daniel.exception;

import br.net.silva.daniel.shared.application.exception.GenericException;

public class ClientNotExistsException extends GenericException {
    public ClientNotExistsException(String message) {
        super(message);
    }
}
