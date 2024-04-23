package br.net.silva.business.validations;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.exception.CreditCardDeactivatedException;
import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.business.exception.CreditCardNumberDifferentException;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.util.Optional;

public class CreditCardNumberExistsValidate implements IValidations, Validation<AccountOutput> {

    private final FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    public CreditCardNumberExistsValidate() {
        this.findAccountGateway = null;
    }

    public CreditCardNumberExistsValidate(FindApplicationBaseGateway<AccountOutput> findAccountGateway) {
        this.findAccountGateway = findAccountGateway;
    }

    @Override
    public void validate(Source param) throws GenericException {
        var input = (DeactivateCreditCardInput) param.input();
        var accountOutput = findAccountGateway.findById(input).orElseThrow(() -> new AccountNotExistsException("Account not exists"));
        var account = AccountBuilder.buildAggregate().createFrom(accountOutput);

        if (!account.isHaveCreditCard()) {
            throw new CreditCardNotExistsException("Credit card not exists in the account");
        }

        var creditCardDto = account.build().creditCard();

        if (!creditCardDto.number().equals(input.creditCardNumber())) {
            throw new CreditCardNumberDifferentException("Credit Card number is different at register in account");
        }

        if (!creditCardDto.active()) {
            throw new CreditCardDeactivatedException("Credit card deactivated in the account");
        }
    }

    @Override
    public void validate(Optional<AccountOutput> opt) throws GenericException {
        final var account = opt.orElseThrow(GenericErrorUtils::executeExceptionNotPermissionOperation);
        final var creditCard = account.creditCard();

        if (creditCard == null) {
            throw new CreditCardNotExistsException("Credit card not exists in the account");
        }

        if (!creditCard.active()) {
            throw new CreditCardDeactivatedException("Credit card deactivated in the account");
        }

    }
}
