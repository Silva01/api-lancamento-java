package br.net.silva.daniel.transaction.listener.transactionlistener.domain.validation;

import br.net.silva.daniel.shared.business.exception.GenericException;

public interface Validation <T> {
    void validate(T t) throws GenericException;
}
