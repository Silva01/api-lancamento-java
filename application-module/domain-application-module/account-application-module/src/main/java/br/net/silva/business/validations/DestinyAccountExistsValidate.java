package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.input.RegisterTransactionInput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class DestinyAccountExistsValidate implements IValidations {

    private final Repository<Optional<Account>> findDetinyAccountRepository;

    public DestinyAccountExistsValidate(Repository<Optional<Account>> findDetinyAccountRepository) {
        this.findDetinyAccountRepository = findDetinyAccountRepository;
    }

    @Override
    public void validate(Source param) throws GenericException {
        var input = (RegisterTransactionInput) param.input();
        var destinyAccount = findDetinyAccountRepository.exec(input.destinyAccountNumber(), input.destinyAgency());

        if (destinyAccount.isEmpty()) {
            throw new AccountNotExistsException("Destiny account not found");
        }
    }
}
