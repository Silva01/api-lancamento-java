package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.component;

import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.IValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.TransactionValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.ValidatorConfigurator;
import org.springframework.stereotype.Component;

@Component
public class TransactionDuplicatedValidator implements IValidation {
    @Override
    public boolean isExecute(TransactionValidation transactionValidation) {
        return transactionValidation.validateIfTransactionIsDuplicated();
    }

    @Override
    public void executeValidation(ValidatorConfigurator configurator) throws GenericException {
        if (configurator.accountConfigValidation().transactionIsDuplicated()) {
            throw new GenericException("Transaction is duplicated");
        }
    }
}
