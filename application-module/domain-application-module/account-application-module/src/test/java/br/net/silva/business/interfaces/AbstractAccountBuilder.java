package br.net.silva.business.interfaces;

import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.CreditCard;
import br.net.silva.daniel.entity.Transaction;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAccountBuilder extends GeneratorRandomValues {

    protected Account buildMockAccount(boolean active, CreditCard creditCard) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", creditCard, new ArrayList<>());
    }

    protected Account buildMockAccount(boolean active, CreditCard creditCard, List<Transaction> transactions) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", creditCard, transactions);
    }

    protected CreditCard buildMockCreditCard(boolean activate) {
        return new CreditCard("99988877766", 45678, FlagEnum.MASTER_CARD, BigDecimal.valueOf(1000), LocalDate.of(2027, 1, 1), activate);
    }

    protected Transaction buildMockTransaction(TransactionTypeEnum type) {
        return new Transaction(generateRandomIdTransaction(), "Compra de teste", BigDecimal.valueOf(1000), 1, type, generateRandomAccountNumber(), generateRandomAccountNumber(), generateIdempotencyId(), null, null);
    }

    protected TransactionInput buildMockInputTransaction(TransactionTypeEnum type, String creditCardNumber, Integer cvv) {
        return new TransactionInput(generateRandomNumberLong(4), "Compra de teste", BigDecimal.valueOf(1000), 1, type, generateIdempotencyId(), creditCardNumber, cvv);
    }

}
