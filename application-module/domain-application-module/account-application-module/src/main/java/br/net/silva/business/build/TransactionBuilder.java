package br.net.silva.business.build;

import br.net.silva.business.factory.TransactionDtoFactory;
import br.net.silva.business.factory.TransactionOutputFactory;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.entity.Transaction;
import br.net.silva.daniel.shared.application.interfaces.IGenericBuilder;

import java.util.List;

public final class TransactionBuilder {

    private TransactionBuilder() {
    }

    public static IGenericBuilder<List<TransactionOutput>, List<TransactionDTO>> buildFullTransactionListOutput() {
        return transactions -> transactions
                .stream()
                .map(transaction -> TransactionOutputFactory
                        .createOutput()
                        .withId(transaction.id())
                        .withDescription(transaction.description())
                        .withPrice(transaction.price())
                        .withQuantity(transaction.quantity())
                        .withType(transaction.type())
                        .withOriginAccountNumber(transaction.originAccountNumber())
                        .withDestinationAccountNumber(transaction.destinationAccountNumber())
                        .withIdempotencyId(transaction.idempotencyId())
                        .withCreditCardNumber(transaction.creditCardNumber())
                        .andWithCreditCardCvv(transaction.creditCardCvv())
                        .build())
                .toList();
    }

    public static IGenericBuilder<List<TransactionDTO>, List<TransactionOutput>> buildFullTransactionListDto() {
        return transactions -> transactions
                .stream()
                .map(transaction -> TransactionDtoFactory
                        .createDto()
                        .withId(transaction.id())
                        .withDescription(transaction.description())
                        .withPrice(transaction.price())
                        .withQuantity(transaction.quantity())
                        .withType(transaction.type())
                        .withOriginAccountNumber(transaction.originAccountNumber())
                        .withDestinationAccountNumber(transaction.destinationAccountNumber())
                        .withIdempotencyId(transaction.idempotencyId())
                        .withCreditCardNumber(transaction.creditCardNumber())
                        .andWithCreditCardCvv(transaction.creditCardCvv())
                        .build())
                .toList();
    }

    public static IGenericBuilder<TransactionOutput, TransactionDTO> buildFullTransactionOutput() {
        return transaction -> TransactionOutputFactory
                .createOutput()
                .withId(transaction.id())
                .withDescription(transaction.description())
                .withPrice(transaction.price())
                .withQuantity(transaction.quantity())
                .withType(transaction.type())
                .withOriginAccountNumber(transaction.originAccountNumber())
                .withDestinationAccountNumber(transaction.destinationAccountNumber())
                .withIdempotencyId(transaction.idempotencyId())
                .withCreditCardNumber(transaction.creditCardNumber())
                .andWithCreditCardCvv(transaction.creditCardCvv())
                .build();
    }

    public static IGenericBuilder<List<Transaction>, List<TransactionOutput>> buildAggregateList() {
        return output -> output
                .stream()
                .map(transaction -> new Transaction(
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
                .toList();
    }
}
