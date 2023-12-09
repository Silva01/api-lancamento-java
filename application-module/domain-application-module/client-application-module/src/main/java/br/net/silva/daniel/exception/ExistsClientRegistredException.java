package br.net.silva.daniel.exception;

import br.net.silva.daniel.GenericException;

public class ExistsClientRegistredException extends GenericException {
    public ExistsClientRegistredException(String message) {
        super(message);
    }
}
