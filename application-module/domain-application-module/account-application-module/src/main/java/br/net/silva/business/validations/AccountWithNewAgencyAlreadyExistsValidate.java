package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyException;
import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Optional;

public class AccountWithNewAgencyAlreadyExistsValidate implements IValidations, Validation<AccountOutput> {

    private final FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    public AccountWithNewAgencyAlreadyExistsValidate() {
        this.findAccountGateway = null;
    }

    @Deprecated(forRemoval = true)
    public AccountWithNewAgencyAlreadyExistsValidate(FindApplicationBaseGateway<AccountOutput> findAccountGateway) {
        this.findAccountGateway = findAccountGateway;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var changeAgencyInput = (ChangeAgencyInput) input.input();
        var inputNewAgency = new ChangeAgencyInput(
                changeAgencyInput.cpf(),
                changeAgencyInput.accountNumber(),
                changeAgencyInput.newAgencyNumber(),
                changeAgencyInput.oldAgencyNumber());
        var optionalAccount = findAccountGateway.findById(inputNewAgency);

        if (optionalAccount.isPresent())
            throw new AccountAlreadyExistsForNewAgencyException("Account With new agency already exists");
    }

    @Override
    public void validate(Optional<AccountOutput> opt) throws GenericException {
        if (opt.isPresent())
            throw new AccountAlreadyExistsForNewAgencyException("Account With new agency already exists");

    }
}
