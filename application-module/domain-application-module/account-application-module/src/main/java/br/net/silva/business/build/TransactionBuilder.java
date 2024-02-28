package br.net.silva.business.build;

import br.net.silva.business.factory.TransactionDtoFactory;
import br.net.silva.business.factory.TransactionOutputFactory;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.interfaces.IGenericBuilder;

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
}
