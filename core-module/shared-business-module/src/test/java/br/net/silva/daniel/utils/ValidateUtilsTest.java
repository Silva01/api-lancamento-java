package br.net.silva.daniel.utils;

import br.net.silva.daniel.shared.business.utils.ValidateUtils;
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
            assertEquals("Balance is insufficient", e.getMessage());
        }
    }

    public void testShouldErrorValidateBalanceLessThanZero() {
        try {
            ValidateUtils.balance(new BigDecimal(-10), new BigDecimal(20));
        } catch (IllegalArgumentException e) {
            assertEquals("Balance is insufficient", e.getMessage());
        }
    }

    public void testShouldValidateIsNotEmpty() {
        ValidateUtils.isNotEmpty("test", "Attribute is empty");

        // If no exception is thrown, the test passes
        assertTrue(true);
    }

    public void testShouldErrorValidateIsNotEmpty() {
        try {
            ValidateUtils.isNotEmpty("", "Attribute is empty");
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is empty", e.getMessage());
        }
    }

    public void testShouldValidateIsNotNull() {
        ValidateUtils.isNotNull("test", "Attribute is null");

        // If no exception is thrown, the test passes
        assertTrue(true);
    }

    public void testShouldErrorValidateIsNotNull() {
        try {
            ValidateUtils.isNotNull(null, "Attribute is null");
        } catch (NullPointerException e) {
            assertEquals("Attribute is null", e.getMessage());
        }
    }

    public void testShouldValidateStringIsNotNullAndNotEmpty() {
        ValidateUtils.isTextNotNullAndNotEmpty("test", "Attribute is null or empty");

        // If no exception is thrown, the test passes
        assertTrue(true);
    }

    public void testShouldErrorValidateStringIsNotNullAndNotEmpty() {
        try {
            ValidateUtils.isTextNotNullAndNotEmpty(null, "Attribute is null or empty");
        } catch (NullPointerException e) {
            assertEquals("Attribute is null or empty", e.getMessage());
        }

        try {
            ValidateUtils.isTextNotNullAndNotEmpty("", "Attribute is null or empty");
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is null or empty", e.getMessage());
        }
    }

    public void testShouldValidateAttributeLessThanZero() {
        ValidateUtils.isLessThanZero(new BigDecimal(10), "Attribute is less than zero");

        // If no exception is thrown, the test passes
        assertTrue(true);
    }

    public void testShouldErrorValidateAttributeLessThanZero() {
        try {
            ValidateUtils.isLessThanZero(new BigDecimal(-10), "Attribute is less than zero");
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeIntegerLessThanZero() {
        try {
            ValidateUtils.isLessThanZero(-10, "Attribute is less than zero");
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeLongLessThanZero() {
        try {
            ValidateUtils.isLessThanZero(-10L, "Attribute is less than zero");
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeDoubleLessThanZero() {
        try {
            ValidateUtils.isLessThanZero(-2.2D, "Attribute is less than zero");
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeIntegerEqualsZero() {
        try {
            ValidateUtils.isEqualsZero(0, "Attribute is equals zero");
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeLongEqualsZero() {
        try {
            ValidateUtils.isEqualsZero(0L, "Attribute is equals zero");
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeDoubleEqualsZero() {
        try {
            ValidateUtils.isEqualsZero(0.0D, "Attribute is equals zero");
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeBigDecimalEqualsZero() {
        try {
            ValidateUtils.isEqualsZero(BigDecimal.ZERO, "Attribute is equals zero");
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }
}