package br.net.silva.business.decorator;

import br.net.silva.business.exception.CreditCardNumberDifferentException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.decorator.ValidationDecorator;
import br.net.silva.daniel.shared.business.exception.GenericException;

public final class ValidateCreditCardNumberDecorator implements ValidationDecorator<AccountOutput> {

    private final String creditCardNumberInput;

    public ValidateCreditCardNumberDecorator(String creditCardNumberInput) {
        this.creditCardNumberInput = creditCardNumberInput;
    }

    @Override
    public void decorator(AccountOutput accountOutput) throws GenericException {
        if (!accountOutput.creditCard().number().equals(creditCardNumberInput)) {
            throw new CreditCardNumberDifferentException("Credit Card number is different at register in account");
        }
    }
}
