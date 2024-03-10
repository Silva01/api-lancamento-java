package br.net.silva.business.validations;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.business.exception.CreditCardNumberDifferentException;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.repository.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;

public class CreditCardNumberExistsValidate implements IValidations {

    private final Repository<AccountOutput> findAccountByCpfAndAccountNumberAndAgencyRepository;

    public CreditCardNumberExistsValidate(Repository<AccountOutput> findAccountByCpfAndAccountNumberAndAgencyRepository) {
        this.findAccountByCpfAndAccountNumberAndAgencyRepository = findAccountByCpfAndAccountNumberAndAgencyRepository;
    }

    @Override
    public void validate(Source param) throws GenericException {
        var input = (DeactivateCreditCardInput) param.input();
        var account = AccountBuilder.buildAggregate().createFrom(findAccountByCpfAndAccountNumberAndAgencyRepository.exec(input.accountNumber(), input.agency(), input.cpf()));

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
