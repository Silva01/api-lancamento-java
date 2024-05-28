package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class ReversalTransactionNotFoundException extends GenericException {
    public ReversalTransactionNotFoundException(String message) {
        super(message);
    }
}
