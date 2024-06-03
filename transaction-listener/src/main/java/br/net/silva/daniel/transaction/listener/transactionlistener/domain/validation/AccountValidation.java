package br.net.silva.daniel.transaction.listener.transactionlistener.domain.validation;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;

import java.util.Optional;

public class AccountValidation implements Validation<Optional<Account>> {
    @Override
    public void validate(Optional<Account> accountOutput) throws GenericException {
        if (accountOutput.isEmpty()) {
            throw new AccountNotExistsException("Account not found");
        }
    }
}
