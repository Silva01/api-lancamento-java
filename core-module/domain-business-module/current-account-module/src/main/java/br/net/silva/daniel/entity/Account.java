package br.net.silva.daniel.entity;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.interfaces.AggregateRoot;
import br.net.silva.daniel.interfaces.IFactory;
import br.net.silva.daniel.utils.GeneratorRandomNumber;
import br.net.silva.daniel.validation.Validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account extends Validation implements AggregateRoot, IFactory<AccountDTO> {

    private Integer number;
    private final Integer bankAgencyNumber;
    private BigDecimal balance;
    private final String password;
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
        this(number, bankAgencyNumber, BigDecimal.ZERO, password, true, cpf, creditCard, new ArrayList<>());
    }

    public Account (Integer bankAgencyNumber, String password, String cpf, CreditCard creditCard) {
        this(1, bankAgencyNumber, BigDecimal.ZERO, password, true, cpf, creditCard, new ArrayList<>());
        generateAccountNumber();
    }

    @Override
    public void validate() {
        validateAttributeNotNullAndNotEmpty(cpf, "CPF is required");
        validateAttributeNotNullAndNotEmpty(password, "Password is required");
        validateAttributeNonNull(bankAgencyNumber, "Bank agency number is required");
        validateAttributeNonNull(number, "Account number is required");
        validateAttributeNonNull(balance, "Balance is required");
        validateAttributeLessThanZero(BigDecimal.valueOf(bankAgencyNumber), "Bank agency number must be greater than zero");
        validateAttributeEqualsZero(BigDecimal.valueOf(bankAgencyNumber), "Bank agency number must be greater than zero");
        validateAttributeLessThanZero(BigDecimal.valueOf(number), "Account number must be greater than zero");
        validateAttributeEqualsZero(BigDecimal.valueOf(number), "Account number must be greater than zero");
        validateAttributeLessThanZero(balance, "Balance must be greater than zero");
    }

    public void registerTransaction(List<TransactionDTO> transactions) {
        var total = transactions.stream().map(TransactionDTO::price).reduce(BigDecimal.ZERO, BigDecimal::add);
        validateBalance(balance, total);
        transactions.forEach(transaction -> this.transactions.add(new Transaction(
                transaction.id(),
                transaction.description(),
                transaction.price(),
                transaction.quantity(),
                transaction.type(),
                transaction.originAccountNumber(),
                transaction.destinationAccountNumber(),
                transaction.idempotencyId(),
                transaction.creditCardNumber(),
                transaction.creditCardCvv())));

        this.balance = this.balance.subtract(total);
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

    @Override
    public AccountDTO create() {
        return new AccountDTO(number, bankAgencyNumber, balance, password, active, cpf, transactions.stream().map(Transaction::create).toList());
    }

    private void validatePassword() {
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be greater than 6");
        }
    }
}
