package br.net.silva.daniel.exception;

import br.net.silva.daniel.shared.application.exception.GenericException;

public class ExistsClientRegistredException extends GenericException {
    public ExistsClientRegistredException(String message) {
        super(message);
    }
}
