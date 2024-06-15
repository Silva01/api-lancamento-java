package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces;

import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.ValidatorConfigurator;

public interface IValidation {

    boolean isExecute(TransactionValidation transactionValidation);

    void executeValidation(ValidatorConfigurator configurator) throws GenericException;
}
