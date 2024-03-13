package br.net.silva.business.validations;

import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;

import java.util.Optional;

public class AccountAlreadyExistsCreditCardValidation implements IValidations {

    private final Repository<Optional<AccountOutput>> findAccountRepository;

    public AccountAlreadyExistsCreditCardValidation(Repository<Optional<AccountOutput>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var createCreditCardInput = (CreateCreditCardInput) input.input();
        var optionalAccount = findAccountRepository.exec(createCreditCardInput.accountNumber(), createCreditCardInput.agency(), createCreditCardInput.cpf());

        var account = optionalAccount.get();

        if (account.creditCard() != null){
            throw new GenericException("This account already have a credit card");
        }
    }
}
