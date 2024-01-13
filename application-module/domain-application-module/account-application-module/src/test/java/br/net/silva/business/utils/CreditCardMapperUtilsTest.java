package br.net.silva.business.utils;

import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.mapper.MapToAccountMapper;
import br.net.silva.business.mapper.MapToAccountMapperImpl;
import br.net.silva.daniel.dto.CreditCardDTO;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardMapperUtilsTest {

    private MapToAccountMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new MapToAccountMapperImpl();
    }

    @Test
    void shouldConvertMapToCreditCardDTOWithSucess() {
        Map<String, Object> creditCardMap = new HashMap<>();
        creditCardMap.put("number", "123456");
        creditCardMap.put("cvv", 123);
        creditCardMap.put("balance", BigDecimal.valueOf(1000.00));
        creditCardMap.put("expirationDate", LocalDate.of(2023, 1,13));
        creditCardMap.put("active", true);
        creditCardMap.put("flag", FlagEnum.MASTER_CARD);

        Map<String, Object> accountMap = new HashMap<>();
        accountMap.put(TypeAccountMapperEnum.CREDIT_CARD.name(), creditCardMap);

        Map<String, Object> principalMap = new HashMap<>();
        principalMap.put(TypeAccountMapperEnum.ACCOUNT.name(), accountMap);

        var source = new Source(principalMap);

        var creditCardDTO = mapper.mapToCreditCardDTO(source);
        assertNotNull(creditCardDTO);
        assertEquals("123456", creditCardDTO.number());
        assertEquals(123, creditCardDTO.cvv());
        assertEquals(BigDecimal.valueOf(1000.00), creditCardDTO.balance());
        assertEquals(LocalDate.of(2023, 1,13), creditCardDTO.expirationDate());
        assertTrue(creditCardDTO.active());
        assertEquals(FlagEnum.MASTER_CARD, creditCardDTO.flag());
    }

}