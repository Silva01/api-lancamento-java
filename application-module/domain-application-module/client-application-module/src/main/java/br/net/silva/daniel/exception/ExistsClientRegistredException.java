package br.net.silva.daniel.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class ExistsClientRegistredException extends GenericException {
    public ExistsClientRegistredException(String message) {
        super(message);
    }
}
