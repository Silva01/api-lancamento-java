package br.net.silva.daniel.validation;

import junit.framework.TestCase;

import java.math.BigDecimal;

public class ValidationTest extends TestCase {

    private Validation validation;

    private Validation createValidation() {
        return new Validation() {
            @Override
            public void validate() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public void testShouldErrorValidateAttributeNonNull() {
        validation = createValidation();
        try {
            validation.validateAttributeNonNull(null, "Attribute is null");
            fail();
        } catch (NullPointerException e) {
            assertEquals("Attribute is null", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeNotEmpty() {
        validation = createValidation();
        try {
            validation.validateAttributeNotEmpty("", "Attribute is empty");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is empty", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeNotNullAndNotEmpty() {
        validation = createValidation();
        try {
            validation.validateAttributeNotNullAndNotEmpty("", "Attribute is null or empty");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is null or empty", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeLessThanZero() {
        validation = createValidation();
        try {
            validation.validateAttributeLessThanZero(-1, "Attribute is less than zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeEqualsZero() {
        validation = createValidation();
        try {
            validation.validateAttributeEqualsZero(0, "Attribute is equals zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeEqualsZeroLong() {
        validation = createValidation();
        try {
            validation.validateAttributeEqualsZero(0L, "Attribute is equals zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeEqualsZeroDouble() {
        validation = createValidation();
        try {
            validation.validateAttributeEqualsZero(0D, "Attribute is equals zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeEqualsZeroBigDecimal() {
        validation = createValidation();
        try {
            validation.validateAttributeEqualsZero(0.0D, "Attribute is equals zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeEqualsZeroInteger() {
        validation = createValidation();
        try {
            validation.validateAttributeEqualsZero(0, "Attribute is equals zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeLessThanZeroBigDecimal() {
        validation = createValidation();
        try {
            validation.validateAttributeEqualsZero(BigDecimal.ZERO, "Attribute is less than zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeLessThanZeroInteger() {
        validation = createValidation();
        try {
            validation.validateAttributeLessThanZero(-1, "Attribute is less than zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeLessThanZeroLong() {
        validation = createValidation();
        try {
            validation.validateAttributeLessThanZero(-1L, "Attribute is less than zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeLessThanZeroDouble() {
        validation = createValidation();
        try {
            validation.validateAttributeLessThanZero(-1.0D, "Attribute is less than zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateBalanceLessThanValue() {
        validation = createValidation();
        try {
            validation.validateBalance(BigDecimal.ZERO, BigDecimal.valueOf(10));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Balance is less than value", e.getMessage());
        }
    }
}