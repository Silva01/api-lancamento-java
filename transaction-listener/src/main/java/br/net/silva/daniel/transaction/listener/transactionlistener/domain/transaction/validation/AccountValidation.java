package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.validation;

import br.net.silva.business.exception.AccountDeactivatedException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;

import java.util.Optional;

public class AccountValidation implements Validation<Optional<Account>> {

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public void validate(Optional<Account> accountOutput) throws GenericException {
        accountExists(accountOutput);
        isAccountActive(accountOutput.get());
    }

    private static void accountExists(Optional<Account> accountOutput) throws AccountNotExistsException {
        if (accountOutput.isEmpty()) {
            throw new AccountNotExistsException("Account not found");
        }
    }

    private static void isAccountActive(Account account) throws AccountDeactivatedException {
        if (!account.isActive()) {
            throw new AccountDeactivatedException("Account is not active");
        }
    }
}
