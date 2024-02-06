package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.daniel.dto.CreditCardDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class TransactionIfCreditCardIsValidValidation implements IValidations {

    private final Repository<Optional<Account>> findAccountRepository;

    public TransactionIfCreditCardIsValidValidation(Repository<Optional<Account>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
    }


    @Override
    public void validate(Source param) throws GenericException {
        var input = (BatchTransactionInput) param.input();
        var optionalAccount = findAccountRepository.exec(input.sourceAccount().accountNumber(), input.sourceAccount().agency(), input.sourceAccount().cpf());

        var creditCardDto = validate(optionalAccount);

        var transactionsWithError = input.batchTransaction()
                .stream()
                .filter(transaction -> TransactionTypeEnum.CREDIT.equals(transaction.type()))
                .filter(transaction -> validateInfoCreditCard(transaction, creditCardDto))
                .toList();

        if (!transactionsWithError.isEmpty()) {
            throw new GenericException("Credit card number or cvv is different at register in account");
        }
    }

    private static CreditCardDTO validate(Optional<Account> optionalAccount) throws AccountNotExistsException, CreditCardNotExistsException {
        if (optionalAccount.isEmpty()) {
            throw new AccountNotExistsException("Account not exists");
        }

        var account = optionalAccount.get();

        if (!account.isHaveCreditCard()) {
            throw new CreditCardNotExistsException("Credit card not exists in the account");
        }

        var creditCardDto = account.build().creditCard();

        if (!creditCardDto.active()) {
            throw new CreditCardNotExistsException("Credit card deactivated in the account");
        }
        return creditCardDto;
    }

    private boolean validateInfoCreditCard(TransactionInput transaction, CreditCardDTO creditCardDto) {
        return !creditCardDto.number().equals(transaction.creditCardNumber()) || !creditCardDto.cvv().equals(transaction.creditCardCvv());
    }
}
