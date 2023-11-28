package br.net.silva.daniel.entity;

import br.net.silva.daniel.enuns.FlagEnum;
import junit.framework.TestCase;

import java.math.BigDecimal;

public class CreditCardTest extends TestCase {

    public void testShouldCreateCreditCardWithSuccess() {
        CreditCard creditCard = new CreditCard();
        assertNotNull(creditCard);

        var dto = creditCard.create();
        assertNotNull(dto);
        assertNotNull(dto.number());
        assertNotNull(dto.cvv());
        assertEquals(FlagEnum.MASTER_CARD, dto.flag());
        assertEquals(BigDecimal.valueOf(2000), dto.balance());
        assertNotNull(dto.expirationDate());
        assertTrue(dto.active());
    }

    public void testShouldCreateCreditCardWithSuccessWithFlag() {
        CreditCard creditCard = new CreditCard(FlagEnum.MASTER_CARD);
        assertNotNull(creditCard);

        var dto = creditCard.create();
        assertNotNull(dto);
        assertNotNull(dto.number());
        assertNotNull(dto.cvv());
        assertEquals(FlagEnum.MASTER_CARD, dto.flag());
        assertEquals(BigDecimal.valueOf(2000), dto.balance());
        assertNotNull(dto.expirationDate());
        assertTrue(dto.active());
    }

    public void testShouldErrorCreateCreditCardWithNumberNullOrEmpty() {
        try {
            new CreditCard(null, 123, FlagEnum.MASTER_CARD, BigDecimal.valueOf(2000), null, true);
            fail();
        } catch (Exception e) {
            assertEquals("Number of credit card cannot be null or empty", e.getMessage());
        }

        try {
            new CreditCard("", 123, FlagEnum.MASTER_CARD, BigDecimal.valueOf(2000), null, true);
            fail();
        } catch (Exception e) {
            assertEquals("Number of credit card cannot be null or empty", e.getMessage());
        }
    }

    public void testShouldErrorCreateCreditCardWithCvvNullOrZeroOrThanLessZero() {
        try {
            new CreditCard("1234567890123456", null, FlagEnum.MASTER_CARD, BigDecimal.valueOf(2000), null, true);
            fail();
        } catch (Exception e) {
            assertEquals("Key secret of credit card cannot be null", e.getMessage());
        }

        try {
            new CreditCard("1234567890123456", 0, FlagEnum.MASTER_CARD, BigDecimal.valueOf(2000), null, true);
            fail();
        } catch (Exception e) {
            assertEquals("Key secret of credit card cannot be zero", e.getMessage());
        }

        try {
            new CreditCard("1234567890123456", -1, FlagEnum.MASTER_CARD, BigDecimal.valueOf(2000), null, true);
            fail();
        } catch (Exception e) {
            assertEquals("Key secret of credit card cannot be less than zero", e.getMessage());
        }
    }

    public void testShouldErrorCreatingCreditCardWithFlagNull() {
        try {
            new CreditCard("1234567890123456", 123, null, BigDecimal.valueOf(2000), null, true);
            fail();
        } catch (Exception e) {
            assertEquals("This flag of credit card cannot be null", e.getMessage());
        }
    }

    public void testShouldErrorCreatingCreditCardWithBalanceNull() {
        try {
            new CreditCard("1234567890123456", 123, FlagEnum.MASTER_CARD, null, null, true);
            fail();
        } catch (Exception e) {
            assertEquals("This balance of credit card cannot be null", e.getMessage());
        }
    }

    public void testShouldErrorCreatingCreditCardWithExpirationDateNull() {
        try {
            new CreditCard("1234567890123456", 123, FlagEnum.MASTER_CARD, BigDecimal.valueOf(2000), null, true);
            fail();
        } catch (Exception e) {
            assertEquals("Date of expiration of credit card cannot be null", e.getMessage());
        }
    }

}