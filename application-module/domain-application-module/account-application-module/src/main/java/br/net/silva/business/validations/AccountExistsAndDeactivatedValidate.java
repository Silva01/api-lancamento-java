package br.net.silva.business.validations;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountAlreadyActiveException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Optional;

public class AccountExistsAndDeactivatedValidate implements IValidations, Validation<AccountOutput> {

    private final FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    public AccountExistsAndDeactivatedValidate() {
        this.findAccountGateway = null;
    }

    public AccountExistsAndDeactivatedValidate(FindApplicationBaseGateway<AccountOutput> findAccountGateway) {
        this.findAccountGateway = findAccountGateway;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var dto = (IAccountParam) input.input();
        var optionalAccount = findAccountGateway.findById(dto);

        if (optionalAccount.isEmpty()) {
            throw new AccountNotExistsException("Account not exists");
        }

        var accountDto = AccountBuilder.buildFullAccountDto().createFrom(optionalAccount.get());
        if (accountDto.active()) {
            throw new AccountAlreadyActiveException("Account already active");
        }

    }

    @Override
    public void validate(Optional<AccountOutput> opt) throws GenericException {
        if (opt.isEmpty()) {
            throw new AccountNotExistsException("Account not exists");
        }

        if (opt.get().active()) {
            throw new AccountAlreadyActiveException("Account already active");
        }
    }
}
