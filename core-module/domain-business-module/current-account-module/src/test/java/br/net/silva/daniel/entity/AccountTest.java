package br.net.silva.daniel.entity;

import junit.framework.TestCase;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AccountTest extends TestCase {

    public void testShouldCreateAccountWithSuccess() {
        var account = new Account(1, 1, "123456", "12345678910");
        var dto = account.create();
        assertNotNull(dto);
    }

    public void testShouldErrorWhenCreateAccountWithNullNumberAndLessZeroAndEqualZero() {
        try {
            new Account(null, 1, "123456", "12345678910");
            fail();
        } catch (Exception e) {
            assertEquals("Account number is required", e.getMessage());
        }

        try {
            new Account(0, 1, "123456", "12345678910");
            fail();
        } catch (Exception e) {
            assertEquals("Account number must be greater than zero", e.getMessage());
        }

        try {
            new Account(-1, 1, "123456", "12345678910");
            fail();
        } catch (Exception e) {
            assertEquals("Account number must be greater than zero", e.getMessage());
        }
    }

    public void testShouldErrorWhenCreateAccountWithNullBankAgencyNumberAndLessZeroAndEqualZero() {
        try {
            new Account(1, null, "123456", "12345678910");
            fail();
        } catch (Exception e) {
            assertEquals("Bank agency number is required", e.getMessage());
        }

        try {
            new Account(1, 0, "123456", "12345678910");
            fail();
        } catch (Exception e) {
            assertEquals("Bank agency number must be greater than zero", e.getMessage());
        }

        try {
            new Account(1, -1, "123456", "12345678910");
            fail();
        } catch (Exception e) {
            assertEquals("Bank agency number must be greater than zero", e.getMessage());
        }
    }

    public void testShouldErrorWhenCreateAccountWithNullBalanceAndLessZero() {
        try {
            new Account(1, 1, null, "123456", true, "12345678910", new ArrayList<>());
            fail();
        } catch (Exception e) {
            assertEquals("Balance is required", e.getMessage());
        }

        try {
            new Account(1, 1, BigDecimal.valueOf(-1), "123456", true, "12345678910", new ArrayList<>());
            fail();
        } catch (Exception e) {
            assertEquals("Balance must be greater than zero", e.getMessage());
        }
    }

    public void testShouldErrorWhenCreateAccountWithNullAndEmptyPassword() {
        try {
            new Account(1, 1, null, "12345678910");
            fail();
        } catch (Exception e) {
            assertEquals("Password is required", e.getMessage());
        }

        try {
            new Account(1, 1, "", "12345678910");
            fail();
        } catch (Exception e) {
            assertEquals("Password is required", e.getMessage());
        }
    }

    public void testShouldErrorWhenCreateAccountWithNullAndEmptyCpf() {
        try {
            new Account(1, 1, "123456", null);
            fail();
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

        try {
            new Account(1, 1, "123456", "");
            fail();
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }
    }

}