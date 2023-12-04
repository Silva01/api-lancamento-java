package br.net.silva.daniel.validation;

import junit.framework.TestCase;

import java.math.BigDecimal;

public class ValidationTest extends TestCase {

    private Validation validation = createValidation();

    private Validation createValidation() {
        return new Validation() {
            @Override
            public void validate() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    public void testShouldErrorValidateAttributeNonNull() {
        try {
            validation.validateAttributeNonNull(null, "Attribute is null");
            fail();
        } catch (NullPointerException e) {
            assertEquals("Attribute is null", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeNotEmpty() {
        try {
            validation.validateAttributeNotEmpty("", "Attribute is empty");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is empty", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeNotNullAndNotEmpty() {
        try {
            validation.validateAttributeNotNullAndNotEmpty("", "Attribute is null or empty");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is null or empty", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeLessThanZero() {
        try {
            validation.validateAttributeLessThanZero(-1, "Attribute is less than zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeEqualsZero() {
        try {
            validation.validateAttributeEqualsZero(0, "Attribute is equals zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeEqualsZeroLong() {
        try {
            validation.validateAttributeEqualsZero(0L, "Attribute is equals zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeEqualsZeroDouble() {
        try {
            validation.validateAttributeEqualsZero(0D, "Attribute is equals zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeEqualsZeroBigDecimal() {
        try {
            validation.validateAttributeEqualsZero(0.0D, "Attribute is equals zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeEqualsZeroInteger() {
        try {
            validation.validateAttributeEqualsZero(0, "Attribute is equals zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is equals zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeLessThanZeroBigDecimal() {
        try {
            validation.validateAttributeEqualsZero(BigDecimal.ZERO, "Attribute is less than zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeLessThanZeroInteger() {
        try {
            validation.validateAttributeLessThanZero(-1, "Attribute is less than zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeLessThanZeroLong() {
        try {
            validation.validateAttributeLessThanZero(-1L, "Attribute is less than zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateAttributeLessThanZeroDouble() {
        try {
            validation.validateAttributeLessThanZero(-1.0D, "Attribute is less than zero");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Attribute is less than zero", e.getMessage());
        }
    }

    public void testShouldErrorValidateBalanceLessThanValue() {
        try {
            validation.validateBalance(BigDecimal.ZERO, BigDecimal.valueOf(10));
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Balance is less than value", e.getMessage());
        }
    }

    public void testShouldSuccessValidateAttributeNonNull() {
        validation.validateAttributeNonNull(new Object(), "Attribute is null");

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateAttributeNotEmpty() {
        validation.validateAttributeNotEmpty("Attribute", "Attribute is empty");

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateAttributeNotNullAndNotEmpty() {
        validation.validateAttributeNotNullAndNotEmpty("Attribute", "Attribute is null or empty");

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateAttributeLessThanZero() {
        validation.validateAttributeLessThanZero(1, "Attribute is less than zero");

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateAttributeEqualsZero() {
        validation.validateAttributeEqualsZero(1, "Attribute is equals zero");

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateAttributeEqualsZeroLong() {
        validation.validateAttributeEqualsZero(1L, "Attribute is equals zero");

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateAttributeEqualsZeroDouble() {
        validation.validateAttributeEqualsZero(1D, "Attribute is equals zero");

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateAttributeEqualsZeroBigDecimal() {
        validation.validateAttributeEqualsZero(BigDecimal.ONE, "Attribute is equals zero");

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateAttributeEqualsZeroInteger() {
        validation.validateAttributeEqualsZero(1, "Attribute is equals zero");

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateAttributeLessThanZeroBigDecimal() {
        validation.validateAttributeLessThanZero(BigDecimal.ONE, "Attribute is less than zero");

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateAttributeLessThanZeroInteger() {
        validation.validateAttributeLessThanZero(1, "Attribute is less than zero");

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateAttributeLessThanZeroLong() {
        validation.validateAttributeLessThanZero(1L, "Attribute is less than zero");

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateAttributeLessThanZeroDouble() {
        validation.validateAttributeLessThanZero(1.0D, "Attribute is less than zero");

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateBalanceLessThanValue() {
        validation.validateBalance(BigDecimal.TEN, BigDecimal.ONE);

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateBalanceEqualsValue() {
        validation.validateBalance(BigDecimal.TEN, BigDecimal.TEN);

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }

    public void testShouldSuccessValidateBalanceGreaterThanValue() {
        validation.validateBalance(BigDecimal.TEN, BigDecimal.valueOf(5));

        // Se chegou aqui é porque não lançou exceção
        assertTrue(true);
    }
}