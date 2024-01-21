package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountAlreadyActiveException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.ICpfParam;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class AccountExistsAndActiveByCpfValidate implements IValidations {

    private final Repository<Optional<Account>> findAccountRepository;

    public AccountExistsAndActiveByCpfValidate(Repository<Optional<Account>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var dto = (ICpfParam) input.input();
        var optionalAccount = findAccountRepository.exec(dto.cpf());

        if (optionalAccount.isEmpty()) {
            throw new AccountNotExistsException("Account not exists");
        }

        var accountDto = optionalAccount.get().build();
        if (!accountDto.active()) {
            throw new AccountAlreadyActiveException("Account is not active");
        }

    }
}
