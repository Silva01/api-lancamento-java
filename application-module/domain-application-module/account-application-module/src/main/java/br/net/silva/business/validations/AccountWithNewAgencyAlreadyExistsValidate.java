package br.net.silva.business.validations;

import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

public class AccountWithNewAgencyAlreadyExistsValidate implements IValidations {

    private final FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    public AccountWithNewAgencyAlreadyExistsValidate(FindApplicationBaseGateway<AccountOutput> findAccountGateway) {
        this.findAccountGateway = findAccountGateway;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var changeAgencyInput = (ChangeAgencyInput) input.input();
        var optionalAccount = findAccountGateway.findById(changeAgencyInput);

        if (optionalAccount.isPresent())
            throw new GenericException("Account with new agency already exists");
    }
}
