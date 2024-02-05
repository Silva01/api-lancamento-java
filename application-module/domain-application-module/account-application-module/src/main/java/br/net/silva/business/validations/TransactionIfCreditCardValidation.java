package br.net.silva.business.validations;

import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

public class TransactionIfCreditCardValidation implements IValidations {

    private final CreditCardNumberExistsValidate creditCardNumberExistsValidate;

    public TransactionIfCreditCardValidation(Repository<Account> findAccountByCpfAndAccountNumberAndAgencyRepository) {
        this.creditCardNumberExistsValidate = new CreditCardNumberExistsValidate(findAccountByCpfAndAccountNumberAndAgencyRepository);
    }

    @Override
    public void validate(Source param) throws GenericException {
        var input = (BatchTransactionInput) param.input();
        final var transactionCreditList = input.batchTransaction().stream().filter(transaction -> TransactionTypeEnum.CREDIT.equals(transaction.type())).toList();

        if (!transactionCreditList.isEmpty()) {
            for (var transaction : transactionCreditList) {
                var inputValidation = new DeactivateCreditCardInput(
                        input.sourceAccount().cpf(),
                        input.sourceAccount().accountNumber(),
                        input.sourceAccount().agency(),
                        transaction.creditCardNumber());

                var source = new Source(inputValidation);
                creditCardNumberExistsValidate.validate(source);
            }
        }
    }
}
