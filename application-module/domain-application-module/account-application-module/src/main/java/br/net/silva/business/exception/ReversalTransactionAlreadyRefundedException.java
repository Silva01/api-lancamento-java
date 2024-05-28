package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class ReversalTransactionAlreadyRefundedException extends GenericException {
    public ReversalTransactionAlreadyRefundedException(String message) {
        super(message);
    }
}
