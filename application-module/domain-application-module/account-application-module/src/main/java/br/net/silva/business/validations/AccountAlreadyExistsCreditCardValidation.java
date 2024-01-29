package br.net.silva.business.validations;

import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class AccountAlreadyExistsCreditCardValidation implements IValidations {

    private final Repository<Optional<Account>> findAccountRepository;

    public AccountAlreadyExistsCreditCardValidation(Repository<Optional<Account>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var createCreditCardInput = (CreateCreditCardInput) input.input();
        var optionalAccount = findAccountRepository.exec(createCreditCardInput.accountNumber(), createCreditCardInput.agency(), createCreditCardInput.cpf());

        var account = optionalAccount.get();

        if (account.isHaveCreditCard()) {
            throw new GenericException("This account already have a credit card");
        }
    }
}
