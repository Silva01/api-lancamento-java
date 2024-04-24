package br.net.silva.business.validations;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountDeactivatedException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Optional;

public class AccountExistsAndActiveValidate implements IValidations, Validation<AccountOutput> {

    private final FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    @Deprecated(forRemoval = true)
    public AccountExistsAndActiveValidate(FindApplicationBaseGateway<AccountOutput> findAccountGateway) {
        this.findAccountGateway = findAccountGateway;
    }

    public AccountExistsAndActiveValidate() {
        this.findAccountGateway = null;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var dto = (IAccountParam) input.input();
        var optionalAccount = findAccountGateway.findById(dto);

        if (optionalAccount.isEmpty()) {
            throw new AccountNotExistsException("Account not exists");
        }

        var accountDto = AccountBuilder.buildFullAccountDto().createFrom(optionalAccount.get());
        if (!accountDto.active()) {
            throw new AccountDeactivatedException("Account is Deactivated");
        }

    }

    @Override
    public void validate(Optional<AccountOutput> optAccount) throws GenericException {
        if (optAccount.isEmpty()) {
            throw new AccountNotExistsException("Account not found");
        }

        if (!optAccount.get().active()) {
            throw new AccountDeactivatedException("Account is Deactivated");
        }
    }
}
