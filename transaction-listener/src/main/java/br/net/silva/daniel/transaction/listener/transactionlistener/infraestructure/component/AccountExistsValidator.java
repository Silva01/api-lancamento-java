package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.component;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.IValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.TransactionValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.ValidatorConfigurator;
import org.springframework.stereotype.Component;

@Component
public class AccountExistsValidator implements IValidation {

    @Override
    public boolean isExecute(TransactionValidation transactionValidation) {
        return transactionValidation.validateIfAccountExists();
    }

    @Override
    public void executeValidation(ValidatorConfigurator configurator) throws AccountNotExistsException {
        if (configurator.accountConfigValidation().accountNotExists()) {
            throw new AccountNotExistsException("Account not found");
        }
    }
}
