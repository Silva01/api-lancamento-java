package br.net.silva.business.validations;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountAlreadyActiveException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.interfaces.IAccountParam;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class AccountExistsAndActiveValidate implements IValidations {

    private final Repository<Optional<AccountOutput>> findAccountRepository;

    public AccountExistsAndActiveValidate(Repository<Optional<AccountOutput>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var dto = (IAccountParam) input.input();
        var optionalAccount = findAccountRepository.exec(dto.accountNumber(), dto.agency(), dto.cpf());

        if (optionalAccount.isEmpty()) {
            throw new AccountNotExistsException("Account not exists");
        }

        var accountDto = AccountBuilder.buildFullAccountDto().createFrom(optionalAccount.get());
        if (accountDto.active()) {
            throw new AccountAlreadyActiveException("Account already active");
        }

    }
}
