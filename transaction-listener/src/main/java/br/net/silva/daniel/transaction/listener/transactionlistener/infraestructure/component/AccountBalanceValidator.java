package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.component;

import br.net.silva.business.exception.AccountBalanceInsufficientExceptionDomain;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.IValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.TransactionValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.aggregate.BaseAccountAggregate;
import org.springframework.stereotype.Component;

@Component
public class AccountBalanceValidator implements IValidation {
    @Override
    public boolean isExecute(TransactionValidation transactionValidation) {
        return transactionValidation.validateIfBalanceIsSufficient();
    }

    @Override
    public void executeValidation(BaseAccountAggregate configurator) throws DomainGenericException {
        if (configurator.accountConfigValidation().balance().compareTo(configurator.totalTransaction()) < 0) {
            throw new AccountBalanceInsufficientExceptionDomain("Insufficient balance");
        }
    }
}
