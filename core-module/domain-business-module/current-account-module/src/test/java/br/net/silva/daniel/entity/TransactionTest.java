package br.net.silva.daniel.entity;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import junit.framework.TestCase;

import java.math.BigDecimal;

public class TransactionTest extends TestCase {

    public void testCreateDebitTransactionWithSuccess() {
        var transaction = new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, TransactionTypeEnum.DEBIT, 1, 2, 1L, null, null);
        assertNotNull(transaction);

        var dto = transaction.create();
        assertEquals(TransactionTypeEnum.DEBIT, dto.type());
    }

    public void testCreateCreditTransactionWithSuccess() {
        var transaction = new Transaction(1L, "Credit Transaction", BigDecimal.valueOf(10), 1, TransactionTypeEnum.CREDIT, 1, 2, 1L, "123456789", 123);
        assertNotNull(transaction);

        var dto = transaction.create();
        assertEquals(TransactionTypeEnum.CREDIT, dto.type());
    }

    public void testCreateDebitTransactionWithNullAndEmptyDescription() {
        try {
            new Transaction(1L, null, BigDecimal.valueOf(10), 1, TransactionTypeEnum.DEBIT, 1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Description is null or empty", e.getMessage());
        }

        try {
            new Transaction(1L, "", BigDecimal.valueOf(10), 1, TransactionTypeEnum.DEBIT, 1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Description is null or empty", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithNullPrice() {
        try {
            new Transaction(1L, "Debit Transaction", null, 1, TransactionTypeEnum.DEBIT, 1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Price is null", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithLessThanZeroPrice() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(-10), 1, TransactionTypeEnum.DEBIT, 1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Price is less than zero", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithNullQuantity() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), null, TransactionTypeEnum.DEBIT, 1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Quantity is null", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithLessThanZeroQuantity() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), -1, TransactionTypeEnum.DEBIT, 1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Quantity is less than zero", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithNullType() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, null, 1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Type is required", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithNullOriginAccountNumber() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, TransactionTypeEnum.DEBIT, null, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Origin account number is null", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithLessThanZeroOriginAccountNumber() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, TransactionTypeEnum.DEBIT, -1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Origin account number is less than zero", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithNullDestinationAccountNumber() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, TransactionTypeEnum.DEBIT, 1, null, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Destination account number is null", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithLessThanZeroDestinationAccountNumber() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, TransactionTypeEnum.DEBIT, 1, -2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Destination account number is less than zero", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithNullIdempotencyId() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, TransactionTypeEnum.DEBIT, 1, 2, null, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Idempotency id is null", e.getMessage());
        }
    }

    public void testCreateCreditTransactionWithCreditCardNumberNullOrEmpty() {
        try {
            new Transaction(1L, "Credit Transaction", BigDecimal.valueOf(10), 1, TransactionTypeEnum.CREDIT, 1, 2, 1L, null, 123);
            fail();
        } catch (Exception e) {
            assertEquals("Credit card number is null or empty", e.getMessage());
        }

        try {
            new Transaction(1L, "Credit Transaction", BigDecimal.valueOf(10), 1, TransactionTypeEnum.CREDIT, 1, 2, 1L, "", 123);
            fail();
        } catch (Exception e) {
            assertEquals("Credit card number is null or empty", e.getMessage());
        }
    }

    public void testCreateCreditTransactionWithCreditCardCvvNullOrEmpty() {
        try {
            new Transaction(1L, "Credit Transaction", BigDecimal.valueOf(10), 1, TransactionTypeEnum.CREDIT, 1, 2, 1L, "123456789", null);
            fail();
        } catch (Exception e) {
            assertEquals("Credit card cvv is null", e.getMessage());
        }

        try {
            new Transaction(1L, "Credit Transaction", BigDecimal.valueOf(10), 1, TransactionTypeEnum.CREDIT, 1, 2, 1L, "123456789", 0);
            fail();
        } catch (Exception e) {
            assertEquals("Credit card cvv is equals zero", e.getMessage());
        }
    }
}