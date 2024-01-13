package br.net.silva.business.utils;

import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.mapper.MapToAccountMapper;
import br.net.silva.business.mapper.MapToAccountMapperImpl;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperUtilsTest {

    private MapToAccountMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new MapToAccountMapperImpl();
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

}