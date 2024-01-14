package br.net.silva.business.utils;

import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.mapper.MapToAccountMapper;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperUtilsTest {

    private MapToAccountMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = MapToAccountMapper.INSTANCE;
    }

    @Test
    void shouldOnlyCreateAccountDtoWithSucess() {
        Map<String, Object> accountMap = new HashMap<>();
        accountMap.put("number", 123);
        accountMap.put("agency", 123);
        accountMap.put("balance", BigDecimal.valueOf(1000.00));
        accountMap.put("password", "123456");
        accountMap.put("active", true);
        accountMap.put("cpf", "12345678910");

        Map<String, Object> principalMap = new HashMap<>();
        principalMap.put(TypeAccountMapperEnum.ACCOUNT.name(), accountMap);

        var source = new Source(principalMap);

        var accountDTO = mapper.mapToAccountDTO(source);

        assertNotNull(accountDTO);
        assertEquals(123, accountDTO.number());
        assertEquals(123, accountDTO.bankAgencyNumber());
        assertEquals(BigDecimal.valueOf(1000.00), accountDTO.balance());
        assertEquals("123456", accountDTO.password());
        assertTrue(accountDTO.active());
        assertEquals("12345678910", accountDTO.cpf());
    }

    @Test
    void shouldOnlyCreateAccountDtoAndCreditCardWithSucess() {
        Map<String, Object> creditCardMap = new HashMap<>();
        creditCardMap.put("number", "123456");
        creditCardMap.put("cvv", 123);
        creditCardMap.put("balance", BigDecimal.valueOf(1000.00));
        creditCardMap.put("expirationDate", LocalDate.of(2023, 1,13));
        creditCardMap.put("active", true);
        creditCardMap.put("flag", FlagEnum.MASTER_CARD);

        Map<String, Object> accountMap = new HashMap<>();
        accountMap.put(TypeAccountMapperEnum.CREDIT_CARD.name(), creditCardMap);
        accountMap.put("number", 123);
        accountMap.put("agency", 123);
        accountMap.put("balance", BigDecimal.valueOf(1000.00));
        accountMap.put("password", "123456");
        accountMap.put("active", true);
        accountMap.put("cpf", "12345678910");

        Map<String, Object> principalMap = new HashMap<>();
        principalMap.put(TypeAccountMapperEnum.ACCOUNT.name(), accountMap);

        var source = new Source(principalMap);

        var accountDTO = mapper.mapToAccountDTO(source);

        assertNotNull(accountDTO);
        assertEquals(123, accountDTO.number());
        assertEquals(123, accountDTO.bankAgencyNumber());
        assertEquals(BigDecimal.valueOf(1000.00), accountDTO.balance());
        assertEquals("123456", accountDTO.password());
        assertTrue(accountDTO.active());
        assertEquals("12345678910", accountDTO.cpf());

        var creditCard = accountDTO.creditCard();
        assertNotNull(creditCard);
        assertEquals("123456", creditCard.number());
        assertEquals(123, creditCard.cvv());
        assertEquals(BigDecimal.valueOf(1000.00), creditCard.balance());
        assertEquals(LocalDate.of(2023, 1,13), creditCard.expirationDate());
        assertTrue(creditCard.active());
        assertEquals(FlagEnum.MASTER_CARD, creditCard.flag());
    }

    @Test
    void shouldCreateCompleteAccountDTO() {
        Map<String, Object> transactionMap = new HashMap<>();
        transactionMap.put("id", 123L);
        transactionMap.put("description", "description");
        transactionMap.put("price", BigDecimal.valueOf(1000.00));
        transactionMap.put("quantity", 1);
        transactionMap.put("type", TransactionTypeEnum.CREDIT);
        transactionMap.put("originAccountNumber", 555);
        transactionMap.put("destinationAccountNumber", 444);
        transactionMap.put("idempotencyId", 333L);
        transactionMap.put("creditCardNumber", "12345678910");
        transactionMap.put("creditCardCvv", 222);

        Map<String, Object> transactionMap2 = new HashMap<>();
        transactionMap2.put("id", 122L);
        transactionMap2.put("description", "description 2");
        transactionMap2.put("price", BigDecimal.valueOf(1002.00));
        transactionMap2.put("quantity", 2);
        transactionMap2.put("type", TransactionTypeEnum.DEBIT);
        transactionMap2.put("originAccountNumber", 552);
        transactionMap2.put("destinationAccountNumber", 442);
        transactionMap2.put("idempotencyId", 332L);
        transactionMap2.put("creditCardNumber", "12345678912");
        transactionMap2.put("creditCardCvv", 221);

        List<Map<String, Object>> transactionsMap = List.of(transactionMap, transactionMap2);

        Map<String, Object> creditCardMap = new HashMap<>();
        creditCardMap.put("number", "123456");
        creditCardMap.put("cvv", 123);
        creditCardMap.put("balance", BigDecimal.valueOf(1000.00));
        creditCardMap.put("expirationDate", LocalDate.of(2023, 1,13));
        creditCardMap.put("active", true);
        creditCardMap.put("flag", FlagEnum.MASTER_CARD);

        Map<String, Object> accountMap = new HashMap<>();
        accountMap.put(TypeAccountMapperEnum.CREDIT_CARD.name(), creditCardMap);
        accountMap.put(TypeAccountMapperEnum.TRANSACTION.name(), transactionsMap);
        accountMap.put("number", 123);
        accountMap.put("agency", 123);
        accountMap.put("balance", BigDecimal.valueOf(1000.00));
        accountMap.put("password", "123456");
        accountMap.put("active", true);
        accountMap.put("cpf", "12345678910");

        Map<String, Object> principalMap = new HashMap<>();
        principalMap.put(TypeAccountMapperEnum.ACCOUNT.name(), accountMap);

        var source = new Source(principalMap);

        var accountDTO = mapper.mapToAccountDTO(source);

        assertNotNull(accountDTO);
        assertEquals(123, accountDTO.number());
        assertEquals(123, accountDTO.bankAgencyNumber());
        assertEquals(BigDecimal.valueOf(1000.00), accountDTO.balance());
        assertEquals("123456", accountDTO.password());
        assertTrue(accountDTO.active());
        assertEquals("12345678910", accountDTO.cpf());

        var creditCard = accountDTO.creditCard();
        assertNotNull(creditCard);
        assertEquals("123456", creditCard.number());
        assertEquals(123, creditCard.cvv());
        assertEquals(BigDecimal.valueOf(1000.00), creditCard.balance());
        assertEquals(LocalDate.of(2023, 1,13), creditCard.expirationDate());
        assertTrue(creditCard.active());
        assertEquals(FlagEnum.MASTER_CARD, creditCard.flag());

        var transactionList = accountDTO.transactions();
        assertNotNull(transactionList);
        assertEquals(2, transactionList.size());

        var transactionDTO1 = transactionList.get(0);
        assertNotNull(transactionDTO1);
        assertEquals(123L, transactionDTO1.id());
        assertEquals("description", transactionDTO1.description());
        assertEquals(BigDecimal.valueOf(1000.00), transactionDTO1.price());
        assertEquals(1, transactionDTO1.quantity());
        assertEquals(TransactionTypeEnum.CREDIT, transactionDTO1.type());
        assertEquals(555, transactionDTO1.originAccountNumber());
        assertEquals(444, transactionDTO1.destinationAccountNumber());
        assertEquals(333L, transactionDTO1.idempotencyId());
        assertEquals("12345678910", transactionDTO1.creditCardNumber());
        assertEquals(222, transactionDTO1.creditCardCvv());

        var transactionDTO2 = transactionList.get(1);
        assertNotNull(transactionDTO2);
        assertEquals(122L, transactionDTO2.id());
        assertEquals("description 2", transactionDTO2.description());
        assertEquals(BigDecimal.valueOf(1002.00), transactionDTO2.price());
        assertEquals(2, transactionDTO2.quantity());
        assertEquals(TransactionTypeEnum.DEBIT, transactionDTO2.type());
        assertEquals(552, transactionDTO2.originAccountNumber());
        assertEquals(442, transactionDTO2.destinationAccountNumber());
        assertEquals(332L, transactionDTO2.idempotencyId());
        assertEquals("12345678912", transactionDTO2.creditCardNumber());
        assertEquals(221, transactionDTO2.creditCardCvv());
    }

}