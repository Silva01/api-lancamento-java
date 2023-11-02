package br.net.silva.daniel.utils;

import junit.framework.TestCase;

import java.math.BigDecimal;

public class ValidateUtilsTest extends TestCase {

    public void testShouldValidateBalance() {
        ValidateUtils.balance(new BigDecimal(10), new BigDecimal(10));

        // If no exception is thrown, the test passes
        assertTrue(true);
    }

    public void testShouldErrorValidateBalanceNull() {
        try {
            ValidateUtils.balance(null, new BigDecimal(10));
        } catch (NullPointerException e) {
            assertEquals("Balance is null", e.getMessage());
        }
    }

    public void testShouldErrorValidateBalanceAndValueNull() {
        try {
            ValidateUtils.balance(null, null);
        } catch (NullPointerException e) {
            assertEquals("Balance is null", e.getMessage());
        }
    }

    public void testShouldErrorValidateBalanceWithValueNull() {
        try {
            ValidateUtils.balance(new BigDecimal(10), null);
        } catch (NullPointerException e) {
            assertEquals("Value is null", e.getMessage());
        }
    }

    public void testShouldErrorValidateBalanceLessThanValue() {
        try {
            ValidateUtils.balance(new BigDecimal(10), new BigDecimal(20));
        } catch (IllegalArgumentException e) {
            assertEquals("Balance is less than value", e.getMessage());
        }
    }
}