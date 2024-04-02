package br.net.silva.daniel.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class ClientDeactivatedException extends GenericException {
    public ClientDeactivatedException(String message) {
        super(message);
    }
}
