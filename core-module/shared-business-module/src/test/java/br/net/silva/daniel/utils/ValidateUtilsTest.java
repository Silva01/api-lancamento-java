package br.net.silva.daniel.utils;

import junit.framework.TestCase;

import java.math.BigDecimal;

public class ValidateUtilsTest extends TestCase {

    public void testShouldErrorValidateBalanceNull() {
        try {
            ValidateUtils.balance(null, new BigDecimal(10));
        } catch (IllegalArgumentException e) {
            assertEquals("Balance is null", e.getMessage());
        }
    }
}