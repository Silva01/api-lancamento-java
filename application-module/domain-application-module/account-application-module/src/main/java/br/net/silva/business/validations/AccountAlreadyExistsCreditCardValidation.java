package br.net.silva.business.validations;

import br.net.silva.business.exception.CreditCardAlreadyExistsException;
import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.value_object.Source;

public class AccountAlreadyExistsCreditCardValidation implements IValidations {

    private final FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    public AccountAlreadyExistsCreditCardValidation(FindApplicationBaseGateway<AccountOutput> findAccountGateway) {
        this.findAccountGateway = findAccountGateway;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var createCreditCardInput = (CreateCreditCardInput) input.input();
        var optionalAccount = findAccountGateway.findById(createCreditCardInput);

        var account = optionalAccount.get();

        if (account.creditCard() != null){
            throw new CreditCardAlreadyExistsException("Credit card already exists");
        }
    }
}
