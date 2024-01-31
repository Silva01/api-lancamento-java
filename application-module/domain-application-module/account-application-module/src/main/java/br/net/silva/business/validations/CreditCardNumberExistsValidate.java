package br.net.silva.business.validations;

import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.business.exception.CreditCardNumberDifferentException;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

public class CreditCardNumberExistsValidate implements IValidations {

    private final Repository<Account> findAccountByCpfAndAccountNumberAndAgencyRepository;

    public CreditCardNumberExistsValidate(Repository<Account> findAccountByCpfAndAccountNumberAndAgencyRepository) {
        this.findAccountByCpfAndAccountNumberAndAgencyRepository = findAccountByCpfAndAccountNumberAndAgencyRepository;
    }

    @Override
    public void validate(Source param) throws GenericException {
        var input = (DeactivateCreditCardInput) param.input();
        var account = findAccountByCpfAndAccountNumberAndAgencyRepository.exec(input.accountNumber(), input.agency(), input.cpf());

        if (!account.isHaveCreditCard()) {
            throw new CreditCardNotExistsException("Credit card not exists in the account");
        }

        var creditCardDto = account.build().creditCard();

        if (!creditCardDto.number().equals(input.creditCardNumber())) {
            throw new CreditCardNumberDifferentException("Credit Card number is different at register in account");
        }

        if (!creditCardDto.active()) {
            throw new CreditCardNotExistsException("Credit card deactivated in the account");
        }
    }
}
