package br.net.silva.business.validations;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountAlreadyActiveException;
import br.net.silva.business.exception.AccountExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;

import java.util.Optional;

public class AccountNotExistsByAgencyAndCPFValidate implements IValidations {

    private final Repository<Optional<AccountOutput>> findAccountRepository;

    public AccountNotExistsByAgencyAndCPFValidate(Repository<Optional<AccountOutput>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var dto = (IAccountParam) input.input();
        var optionalAccountOutput = findAccountRepository.exec(dto.agency(), dto.cpf());

        if (optionalAccountOutput.isPresent()) {
            var accountDto = AccountBuilder.buildFullAccountDto().createFrom(optionalAccountOutput.get());
            if (accountDto.active()) {
                throw new AccountAlreadyActiveException("Account already active");
            }

            throw new AccountExistsException("Account Exists by agency and cpf");
        }



    }
}
