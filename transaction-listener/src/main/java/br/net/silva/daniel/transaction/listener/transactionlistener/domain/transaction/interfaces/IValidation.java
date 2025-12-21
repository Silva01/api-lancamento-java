package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.aggregate.BaseAccountAggregate;

public interface IValidation {

    boolean isExecute(TransactionValidation transactionValidation);

    void executeValidation(BaseAccountAggregate configurator) throws DomainGenericException;
}
