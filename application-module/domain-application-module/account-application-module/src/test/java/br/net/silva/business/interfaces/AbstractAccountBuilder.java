package br.net.silva.business.interfaces;

import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.CreditCardOutput;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAccountBuilder extends GeneratorRandomValues {

    protected AccountOutput buildMockAccount(boolean active, CreditCardOutput creditCard) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", creditCard, new ArrayList<>());
    }

    protected AccountOutput buildMockAccount(boolean active, CreditCardOutput creditCard, List<TransactionOutput> transactions) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", creditCard, transactions);
    }

    protected CreditCardOutput buildMockCreditCard(boolean activate) {
        return new CreditCardOutput("99988877766", 45678, FlagEnum.MASTER_CARD, BigDecimal.valueOf(1000), LocalDate.now().plusYears(3), activate);
    }

    protected CreditCardOutput buildMockCreditCardExpired(boolean activate) {
        return new CreditCardOutput("99988877766", 45678, FlagEnum.MASTER_CARD, BigDecimal.valueOf(1000), LocalDate.now().minusYears(1), activate);
    }

    protected TransactionOutput buildMockTransaction(TransactionTypeEnum type) {
        return new TransactionOutput(generateRandomIdTransaction(), "Compra de teste", BigDecimal.valueOf(1000), 1, type, generateRandomAccountNumber(), generateRandomAccountNumber(), generateIdempotencyId(), null, null);
    }

    protected TransactionInput buildMockInputTransaction(TransactionTypeEnum type, String creditCardNumber, Integer cvv) {
        return new TransactionInput(generateRandomNumberLong(4), "Compra de teste", BigDecimal.valueOf(1000), 1, type, generateIdempotencyId(), creditCardNumber, cvv);
    }

}
