package br.net.silva.business.value_object.output;

import br.net.silva.business.enums.AccountStatusEnum;
import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetInformationAccountOutputTest {

    @Test
    void testGetterAndSetter() {
        // Create an instance of GetInformationAccountOutput using Lombok-generated constructor
        GetInformationAccountOutput output = new GetInformationAccountOutput();

        // Set values using setters
        output.setAgency(123);
        output.setAccountNumber(456);
        output.setStatus(AccountStatusEnum.ACTIVE.name());
        output.setBalance(new BigDecimal("1000.00"));
        output.setHaveCreditCard(true);

        // Create a TransactionDTO for testing

        TransactionDTO transaction = new TransactionDTO(
                1L,
                "Payment",
                new BigDecimal("50.00"),
                1,
                TransactionTypeEnum.CREDIT,
                123,
                321,
                444L,
                "3333",
                111);

        // Add a transaction to the list
        List<TransactionDTO> transactions = new ArrayList<>();
        transactions.add(transaction);
        output.setTransactions(transactions);

        // Validate values using getters
        assertEquals(123, output.getAgency());
        assertEquals(456, output.getAccountNumber());
        assertEquals(AccountStatusEnum.ACTIVE, output.getStatus());
        assertEquals(new BigDecimal("1000.00"), output.getBalance());
        assertTrue(output.isHaveCreditCard());

        // Validate transactions list
        assertEquals(1, output.getTransactions().size());
        assertEquals(1L, output.getTransactions().get(0).id());
        assertEquals("Payment", output.getTransactions().get(0).description());
        assertEquals(new BigDecimal("50.00"), output.getTransactions().get(0).price());
    }

}