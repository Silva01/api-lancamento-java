package br.net.silva.business.validations;

import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class AccountWithNewAgencyAlreadyExistsValidate implements IValidations {

    private final Repository<Optional<Account>> findAccountByNewAgencyNumberAndAccountNumberRepository;

    public AccountWithNewAgencyAlreadyExistsValidate(Repository<Optional<Account>> findAccountByNewAgencyNumberAndAccountNumberRepository) {
        this.findAccountByNewAgencyNumberAndAccountNumberRepository = findAccountByNewAgencyNumberAndAccountNumberRepository;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var changeAgencyInput = (ChangeAgencyInput) input.input();
        var optionalAccount = findAccountByNewAgencyNumberAndAccountNumberRepository.exec(changeAgencyInput.accountNumber(), changeAgencyInput.newAgencyNumber());

        if (optionalAccount.isPresent())
            throw new GenericException("Account with new agency already exists");
    }
}
