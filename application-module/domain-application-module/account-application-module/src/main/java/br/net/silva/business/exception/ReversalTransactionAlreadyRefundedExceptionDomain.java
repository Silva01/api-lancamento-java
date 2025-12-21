package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class ReversalTransactionAlreadyRefundedExceptionDomain extends DomainGenericException {
    public ReversalTransactionAlreadyRefundedExceptionDomain(String message) {
        super(message);
    }
}
