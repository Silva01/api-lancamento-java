package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class TransactionDuplicateException extends GenericException {

    public TransactionDuplicateException(String message) {
        super(message);
    }
}
