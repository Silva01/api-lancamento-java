package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountAlreadyActiveException;
import br.net.silva.business.exception.AccountExistsException;
import br.net.silva.business.interfaces.IAccountParam;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class AccountNotExistsByAgencyAndCPFValidate implements IValidations {

    private final Repository<Optional<Account>> findAccountRepository;

    public AccountNotExistsByAgencyAndCPFValidate(Repository<Optional<Account>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var dto = (IAccountParam) input.input();
        var optionalAccount = findAccountRepository.exec(dto.agency(), dto.cpf());

        if (optionalAccount.isPresent()) {
            var accountDto = optionalAccount.get().build();
            if (accountDto.active()) {
                throw new AccountAlreadyActiveException("Account already active");
            }

            throw new AccountExistsException("Account Exists by agency and cpf");
        }



    }
}
