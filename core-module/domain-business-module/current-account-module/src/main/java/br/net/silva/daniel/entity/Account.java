package br.net.silva.daniel.entity;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.shared.business.factory.IFactoryDto;
import br.net.silva.daniel.shared.business.interfaces.AggregateRoot;
import br.net.silva.daniel.shared.business.utils.GeneratorRandomNumber;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;
import br.net.silva.daniel.shared.business.validation.Validation;
import br.net.silva.daniel.strategy.ICalculation;

import java.math.BigDecimal;
import java.util.*;

public class Account extends Validation implements AggregateRoot, IFactoryDto<AccountDTO> {

    private Integer number;
    private final Integer bankAgencyNumber;
    private BigDecimal balance;
    private String password;
    private boolean active;
    private final String cpf;
    private CreditCard creditCard;
    private final List<Transaction> transactions;

    public Account(Integer number, Integer bankAgencyNumber, BigDecimal balance, String password, boolean active, String cpf, CreditCard creditCard, List<Transaction> transactions) {
        this.number = number;
        this.bankAgencyNumber = bankAgencyNumber;
        this.balance = balance;
        this.password = password;
        this.active = active;
        this.cpf = cpf;
        this.creditCard = creditCard;
        this.transactions = transactions;
        validate();
    }

    public Account(Integer number, Integer bankAgencyNumber, String password, String cpf, CreditCard creditCard) {
        this(number, bankAgencyNumber, BigDecimal.valueOf(2000), password, true, cpf, creditCard, new ArrayList<>());
    }

    public Account (Integer bankAgencyNumber, String password, String cpf) {
        this(1, bankAgencyNumber, BigDecimal.valueOf(2000), password, true, cpf, null, new ArrayList<>());
        generateAccountNumber();
    }

    @Override
    public void validate() {
        validateAttributeNotNullAndNotEmpty(cpf, "CPF is required");
        validatePassword();
        validateAttributeNonNull(bankAgencyNumber, "Bank agency number is required");
        validateAttributeNonNull(number, "Account number is required");
        validateAttributeNonNull(balance, "Balance is required");
        validateAttributeLessThanZero(bankAgencyNumber, "Bank agency number must be greater than zero");
        validateAttributeEqualsZero(bankAgencyNumber, "Bank agency number must be greater than zero");
        validateAttributeLessThanZero(number, "Account number must be greater than zero");
        validateAttributeEqualsZero(number, "Account number must be greater than zero");
        validateAttributeLessThanZero(balance, "Balance must be greater than zero");
    }

    public void registerTransaction(List<TransactionDTO> transactions, ICalculation transactionCal) {
        var debitTotal = transactionCal.calculate(transactions, TransactionTypeEnum.DEBIT);
        var creditTotal = transactionCal.calculate(transactions, TransactionTypeEnum.CREDIT);
        var reversalTotal = transactionCal.calculate(transactions, TransactionTypeEnum.REVERSAL);

        if (isHaveCreditCard()) {
            creditCard.validateBalance(creditTotal);
            creditCard.registerTransactionBalance(creditTotal);
        }

        validateBalance(balance, debitTotal);
        this.transactions.addAll(transactions.stream().map(transaction -> new Transaction(
                transaction.id(),
                transaction.description(),
                transaction.price(),
                transaction.quantity(),
                transaction.type(),
                transaction.originAccountNumber(),
                transaction.destinationAccountNumber(),
                transaction.idempotencyId(),
                transaction.creditCardNumber(),
                transaction.creditCardCvv()))
                .toList());

        debitTotal = debitTotal.add(reversalTotal);
        this.balance = this.balance.subtract(debitTotal);
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    public void generateAccountNumber() {
        this.number = GeneratorRandomNumber.generate(10000);
    }

    public void vinculateCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public void validatePassword(String password) {
        if (!this.password.equals(password)) {
            throw GenericErrorUtils.executeException("Password is different");
        }
    }

    @Override
    public AccountDTO build() {
        return new AccountDTO(
                number,
                bankAgencyNumber,
                balance,
                password,
                active,
                cpf,
                transactions.stream().map(Transaction::build).toList(),
                Objects.nonNull(creditCard) ? creditCard.build() : null);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public boolean isHaveCreditCard() {
        return Objects.nonNull(creditCard);
    }

    public void deactivateCreditCard() {
        creditCard.deactivate();
    }

    private void validatePassword() {
        validateAttributeNotNullAndNotEmpty(password, "Password is required");
    }
}
