package br.net.silva.daniel.entity;

import junit.framework.TestCase;

import java.math.BigDecimal;

public class TransactionTest extends TestCase {

    public void testCreateDebitTransactionWithSuccess() {
        var transaction = new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, "DEBIT", 1, 2, 1L, null, null);
        assertNotNull(transaction);
        assertEquals("DEBIT", transaction.getType());
    }

    public void testCreateCreditTransactionWithSuccess() {
        var transaction = new Transaction(1L, "Credit Transaction", BigDecimal.valueOf(10), 1, "CREDIT", 1, 2, 1L, "123456789", 123);
        assertNotNull(transaction);
        assertEquals("CREDIT", transaction.getType());
    }

    public void testCreateDebitTransactionWithNullAndEmptyDescription() {
        try {
            new Transaction(1L, null, BigDecimal.valueOf(10), 1, "DEBIT", 1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Description is null or empty", e.getMessage());
        }

        try {
            new Transaction(1L, "", BigDecimal.valueOf(10), 1, "DEBIT", 1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Description is null or empty", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithNullPrice() {
        try {
            new Transaction(1L, "Debit Transaction", null, 1, "DEBIT", 1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Price is null", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithLessThanZeroPrice() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(-10), 1, "DEBIT", 1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Price is less than zero", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithNullQuantity() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), null, "DEBIT", 1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Quantity is null", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithLessThanZeroQuantity() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), -1, "DEBIT", 1, 2, 1L, null, null);
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
            assertEquals("Type is null or empty", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithEmptyType() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, "", 1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Type is null or empty", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithNullOriginAccountNumber() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, "DEBIT", null, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Origin account number is null", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithLessThanZeroOriginAccountNumber() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, "DEBIT", -1, 2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Origin account number is less than zero", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithNullDestinationAccountNumber() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, "DEBIT", 1, null, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Destination account number is null", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithLessThanZeroDestinationAccountNumber() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, "DEBIT", 1, -2, 1L, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Destination account number is less than zero", e.getMessage());
        }
    }

    public void testCreateDebitTransactionWithNullIdempotencyId() {
        try {
            new Transaction(1L, "Debit Transaction", BigDecimal.valueOf(10), 1, "DEBIT", 1, 2, null, null, null);
            fail();
        } catch (Exception e) {
            assertEquals("Idempotency id is null", e.getMessage());
        }
    }

}