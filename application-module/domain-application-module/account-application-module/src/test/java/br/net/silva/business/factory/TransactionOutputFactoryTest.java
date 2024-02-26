package br.net.silva.business.factory;

import br.net.silva.business.build.TransactionBuilder;
import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionOutputFactoryTest extends AbstractAccountBuilder {

    @Test
    void shouldCreateTransactionOutputWithSuccess() {
        var transactionMock = buildMockTransaction(TransactionTypeEnum.DEBIT).build();
        var transactionOutput = TransactionBuilder.buildFullTransactionsOutput().createFrom(List.of(transactionMock));

        assertNotNull(transactionOutput);
        assertEquals(1, transactionOutput.size());

        transactionOutput.forEach(output -> {
            assertEquals(transactionMock.id(), output.id());
            assertEquals(transactionMock.description(), output.description());
            assertEquals(transactionMock.price(), output.price());
            assertEquals(transactionMock.quantity(), output.quantity());
            assertEquals(transactionMock.type(), output.type());
            assertEquals(transactionMock.originAccountNumber(), output.originAccountNumber());
            assertEquals(transactionMock.destinationAccountNumber(), output.destinationAccountNumber());
            assertEquals(transactionMock.idempotencyId(), output.idempotencyId());
            assertEquals(transactionMock.creditCardNumber(), output.creditCardNumber());
            assertEquals(transactionMock.creditCardCvv(), output.creditCardCvv());
        });
    }

}