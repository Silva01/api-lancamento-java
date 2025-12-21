package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class ReversalTransactionNotFoundExceptionDomain extends DomainGenericException {
    public ReversalTransactionNotFoundExceptionDomain(String message) {
        super(message);
    }
}
