package br.net.silva.daniel.entity;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.CreditCardDTO;
import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.strategy.CalculateStrategy;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountTest extends TestCase {

    public void testShouldCreateAccountWithSuccess() {
        var account = new Account(1, 1, "123456", "12345678910", null);
        var dto = account.build();
        assertNotNull(dto);
    }

    public void testShouldCreateAccountBasicWithSuccess() {
        var account = new Account(1, "123456", "12345678910");
        var dto = account.build();

        dto.accept(AccountDTO.class);

        assertNotNull(dto);
        assertEquals(Integer.valueOf(1), dto.agency());
        assertEquals("123456", dto.password());
        assertEquals("12345678910", dto.cpf());
        assertNotNull(dto.number());
        assertFalse(dto.number() < 0);
        assertFalse(dto.number().equals(0));

        var dto2 = dto.get();
        assertNotNull(dto2);
        assertEquals(Integer.valueOf(1), dto2.agency());
        assertEquals("123456", dto2.password());
        assertEquals("12345678910", dto2.cpf());
        assertNotNull(dto2.number());
        assertFalse(dto2.number() < 0);
        assertFalse(dto2.number().equals(0));
    }

    public void testShouldErrorWhenCreateAccountWithNullNumberAndLessZeroAndEqualZero() {
        try {
            new Account(null, 1, "123456", "12345678910", null);
            fail();
        } catch (Exception e) {
            assertEquals("Account number is required", e.getMessage());
        }

        try {
            new Account(0, 1, "123456", "12345678910", null);
            fail();
        } catch (Exception e) {
            assertEquals("Account number must be greater than zero", e.getMessage());
        }

        try {
            new Account(-1, 1, "123456", "12345678910", null);
            fail();
        } catch (Exception e) {
            assertEquals("Account number must be greater than zero", e.getMessage());
        }
    }

    public void testShouldErrorWhenCreateAccountWithNullBankAgencyNumberAndLessZeroAndEqualZero() {
        try {
            new Account(1, null, "123456", "12345678910", null);
            fail();
        } catch (Exception e) {
            assertEquals("Bank agency number is required", e.getMessage());
        }

        try {
            new Account(1, 0, "123456", "12345678910", null);
            fail();
        } catch (Exception e) {
            assertEquals("Bank agency number must be greater than zero", e.getMessage());
        }

        try {
            new Account(1, -1, "123456", "12345678910", null);
            fail();
        } catch (Exception e) {
            assertEquals("Bank agency number must be greater than zero", e.getMessage());
        }
    }

    public void testShouldErrorWhenCreateAccountWithNullBalanceAndLessZero() {
        try {
            new Account(1, 1, null, "123456", true, "12345678910", null, new ArrayList<>());
            fail();
        } catch (Exception e) {
            assertEquals("Balance is required", e.getMessage());
        }

        try {
            new Account(1, 1, BigDecimal.valueOf(-1), "123456", true, "12345678910", null, new ArrayList<>());
            fail();
        } catch (Exception e) {
            assertEquals("Balance must be greater than zero", e.getMessage());
        }
    }

    public void testShouldErrorWhenCreateAccountWithNullAndEmptyPassword() {
        try {
            new Account(1, 1, null, "12345678910", null);
            fail();
        } catch (Exception e) {
            assertEquals("Password is required", e.getMessage());
        }

        try {
            new Account(1, 1, "", "12345678910", null);
            fail();
        } catch (Exception e) {
            assertEquals("Password is required", e.getMessage());
        }
    }

    public void testShouldErrorWhenCreateAccountWithNullAndEmptyCpf() {
        try {
            new Account(1, 1, "123456", null, null);
            fail();
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }

        try {
            new Account(1, 1, "123456", "", null);
            fail();
        } catch (Exception e) {
            assertEquals("CPF is required", e.getMessage());
        }
    }

    public void testShouldRegisterTransactionWithSuccess() {
        var transactionDTO = new TransactionDTO(
                1L,
                "Test",
                BigDecimal.valueOf(100),
                1,
                TransactionTypeEnum.DEBIT,
                1,
                2,
                123456L,
                "12345678910",
                123);

        transactionDTO.accept(TransactionDTO.class);

        var account = new Account(1, 1, "123456", "12345678910", null);
        account.registerTransaction(List.of(transactionDTO.get()), CalculateStrategy.calculationBuy());

        var accountDTO = account.build();
        assertEquals(BigDecimal.valueOf(1900), accountDTO.balance());
    }

    public void testShouldRegisterTransactionDebitAndCreditWithSuccess() {
        var transactionDebit = new TransactionDTO(
                1L,
                "Test",
                BigDecimal.valueOf(100),
                1,
                TransactionTypeEnum.DEBIT,
                1,
                2,
                123456L,
                "12345678910",
                123);

        var transactionCredit = new TransactionDTO(
                2L,
                "Test",
                BigDecimal.valueOf(200),
                1,
                TransactionTypeEnum.CREDIT,
                1,
                2,
                123456L,
                "12345678910",
                123);

        transactionDebit.accept(TransactionDTO.class);

        var account = new Account(1, 1, "123456", "12345678910", new CreditCard());
        account.registerTransaction(List.of(transactionDebit, transactionCredit), CalculateStrategy.calculationBuy());

        var accountDTO = account.build();
        assertEquals(BigDecimal.valueOf(1900), accountDTO.balance());
        assertEquals(BigDecimal.valueOf(1800), accountDTO.creditCard().balance());
    }

    public void testShouldRegisterTransactionAndAddMoreBalanceWithSuccess() {
        var transactionDTO = new TransactionDTO(
                1L,
                "Test",
                BigDecimal.valueOf(100),
                1,
                TransactionTypeEnum.DEBIT,
                1,
                2,
                123456L,
                "12345678910",
                123);

        transactionDTO.accept(TransactionDTO.class);

        var account = new Account(1, 1, "123456", "12345678910", null);
        account.registerTransaction(List.of(transactionDTO.get()), CalculateStrategy.calculationSale());

        var accountDTO = account.build();
        assertEquals(BigDecimal.valueOf(2100), accountDTO.balance());
    }

    public void testShouldErrorWhenRegisterTransactionWithBalanceLessThanZero() {
        var transactionDTO = new TransactionDTO(
                1L,
                "Test",
                BigDecimal.valueOf(2001),
                1,
                TransactionTypeEnum.DEBIT,
                1,
                2,
                123456L,
                "12345678910",
                123);

        transactionDTO.accept(TransactionDTO.class);

        var account = new Account(1, 1, "123456", "12345678910", null);
        try {
            account.registerTransaction(List.of(transactionDTO.get()), CalculateStrategy.calculationBuy());
            fail();
        } catch (Exception e) {
            assertEquals("Balance is insufficient", e.getMessage());
        }
    }

    public void testShouldActivateAccountWithSuccess() {
        var account = new Account(1, 1, "123456", "12345678910", null);
        account.activate();

        var dto = account.build();
        assertTrue(dto.active());
    }

    public void testShouldDeactivateAccountWithSuccess() {
        var account = new Account(1, 1, "123456", "12345678910", null);
        account.deactivate();

        var dto = account.build();
        assertFalse(dto.active());
    }

    public void testShouldRegisterCreditCardWithSuccess() {
        var creditCard = new CreditCard();
        var account = new Account(1, 1, "123456", "12345678910", null);
        account.vinculateCreditCard(creditCard);

        var dto = account.build();
        assertNotNull(dto.creditCard());
    }

    public void testMustValidateWithSuccessExternalPassword() {
        var account = new Account(1, 1, "123456", "12345678910", null);
        account.validatePassword("123456");
    }

    public void testMustValidateWithErrorInternalPassword() {
        var account = new Account(1, 1, "123456", "12345678910", null);
        try {
            account.validatePassword("1234567");
            fail();
        } catch (Exception e) {
            assertEquals("Password is different", e.getMessage());
        }
    }

    public void testMustValidateCreditCardDTO() {
        var creditCard = new CreditCard();

        var dto = creditCard.build();
        dto.accept(CreditCardDTO.class);

        assertNotNull(dto.get());
    }

}