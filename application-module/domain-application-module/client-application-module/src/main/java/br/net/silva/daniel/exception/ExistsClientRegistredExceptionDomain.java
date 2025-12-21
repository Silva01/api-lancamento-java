package br.net.silva.daniel.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class ExistsClientRegistredExceptionDomain extends DomainGenericException {
    public ExistsClientRegistredExceptionDomain(String message) {
        super(message);
    }
}
