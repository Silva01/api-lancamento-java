package br.net.silva.daniel.transaction.listener.transactionlistener.domain.validation;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Optional;

public class AccountValidation implements Validation<Optional<AccountOutput>> {
    @Override
    public void validate(Optional<AccountOutput> accountOutput) throws GenericException {
        if (accountOutput.isEmpty()) {
            throw new AccountNotExistsException("Account not found");
        }
    }
}
