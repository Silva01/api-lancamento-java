package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.repository.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;

import java.util.Optional;

public class DestinyAccountExistsValidate implements IValidations {

    private final Repository<Optional<AccountOutput>> findDetinyAccountRepository;

    public DestinyAccountExistsValidate(Repository<Optional<AccountOutput>> findDetinyAccountRepository) {
        this.findDetinyAccountRepository = findDetinyAccountRepository;
    }

    @Override
    public void validate(Source param) throws GenericException {
        var input = (BatchTransactionInput) param.input();
        var destinyAccount = findDetinyAccountRepository.exec(input.destinyAccount().accountNumber(), input.destinyAccount().agency(), input.destinyAccount().cpf());

        if (destinyAccount.isEmpty()) {
            throw new AccountNotExistsException("Destiny account not found");
        }
    }
}
