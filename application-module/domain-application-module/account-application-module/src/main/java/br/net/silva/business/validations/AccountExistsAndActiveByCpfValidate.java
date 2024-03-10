package br.net.silva.business.validations;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountAlreadyActiveException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.repository.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;

import java.util.Optional;

public class AccountExistsAndActiveByCpfValidate implements IValidations {

    private final Repository<Optional<AccountOutput>> findAccountRepository;

    public AccountExistsAndActiveByCpfValidate(Repository<Optional<AccountOutput>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var dto = (ICpfParam) input.input();
        var optionalAccountOutput = findAccountRepository.exec(dto.cpf());

        if (optionalAccountOutput.isEmpty()) {
            throw new AccountNotExistsException("Account not exists");
        }

        var accountDto = AccountBuilder.buildFullAccountDto().createFrom(optionalAccountOutput.get());

        if (!accountDto.active()) {
            throw new AccountAlreadyActiveException("Account is not active");
        }

    }
}
