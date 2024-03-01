package br.net.silva.business.validations;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.exception.CreditCardDeactivatedException;
import br.net.silva.business.exception.CreditCardExpiredException;
import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.CreditCardDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.time.LocalDate;
import java.util.Optional;

public class TransactionIfCreditCardIsValidValidation implements IValidations {

    private final Repository<Optional<AccountOutput>> findAccountRepository;

    public TransactionIfCreditCardIsValidValidation(Repository<Optional<AccountOutput>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
    }


    @Override
    public void validate(Source param) throws GenericException {
        var input = (BatchTransactionInput) param.input();
        final var hasTransactionTypeCredit = input.batchTransaction().stream().anyMatch(transaction -> TransactionTypeEnum.CREDIT.equals(transaction.type()));
        if (!hasTransactionTypeCredit) {
            return;
        }

        var optionalAccount = findAccountRepository.exec(input.sourceAccount().accountNumber(), input.sourceAccount().agency(), input.sourceAccount().cpf());

        if (optionalAccount.isEmpty()) {
            throw new AccountNotExistsException("Account not exists");
        }

        var creditCardDto = validate(AccountBuilder.buildAggregate().createFrom(optionalAccount.get()));

        var transactionsWithError = input.batchTransaction()
                .stream()
                .filter(transaction -> TransactionTypeEnum.CREDIT.equals(transaction.type()))
                .filter(transaction -> validateInfoCreditCard(transaction, creditCardDto))
                .toList();

        if (!transactionsWithError.isEmpty()) {
            throw new GenericException("Credit card number or cvv is different at register in account");
        }
    }

    private static CreditCardDTO validate(Account account) throws GenericException {

        if (!account.isHaveCreditCard()) {
            throw new CreditCardNotExistsException("Credit card not exists in the account");
        }

        var creditCardDto = account.build().creditCard();

        if (!creditCardDto.active()) {
            throw new CreditCardDeactivatedException("Credit card deactivated in the account");
        }

        if (LocalDate.now().isAfter(creditCardDto.expirationDate())) {
            throw new CreditCardExpiredException("Credit card expired");
        }

        return creditCardDto;
    }

    private boolean validateInfoCreditCard(TransactionInput transaction, CreditCardDTO creditCardDto) {
        return !creditCardDto.number().equals(transaction.creditCardNumber()) || !creditCardDto.cvv().equals(transaction.creditCardCvv());
    }
}
