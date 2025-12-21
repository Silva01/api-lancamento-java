package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.component;

import br.net.silva.business.exception.AccountNotExistsExceptionDomain;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.IValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.TransactionValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.aggregate.BaseAccountAggregate;
import org.springframework.stereotype.Component;

@Component
public class AccountExistsValidator implements IValidation {

    @Override
    public boolean isExecute(TransactionValidation transactionValidation) {
        return transactionValidation.validateIfAccountExists();
    }

    @Override
    public void executeValidation(BaseAccountAggregate configurator) throws AccountNotExistsExceptionDomain {
        if (configurator.accountConfigValidation().accountNotExists()) {
            throw new AccountNotExistsExceptionDomain("Account not found");
        }
    }
}
