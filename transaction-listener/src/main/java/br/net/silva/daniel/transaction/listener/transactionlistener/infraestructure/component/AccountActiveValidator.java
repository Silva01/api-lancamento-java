package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.component;

import br.net.silva.business.exception.AccountDeactivatedExceptionDomain;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.IValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.TransactionValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.aggregate.BaseAccountAggregate;
import org.springframework.stereotype.Component;

@Component
public class AccountActiveValidator implements IValidation {
    @Override
    public boolean isExecute(TransactionValidation transactionValidation) {
        return transactionValidation.validateIfAccountIsActive();
    }

    @Override
    public void executeValidation(BaseAccountAggregate configurator) throws DomainGenericException {
        if (configurator.accountConfigValidation().accountIsInactive()) {
            throw new AccountDeactivatedExceptionDomain("Account is not active");
        }
    }
}
