package br.net.silva.business.utils;

import br.net.silva.business.mapper.MapToAccountMapper;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TransactionMapperUtilsTest {

    private MapToAccountMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = MapToAccountMapper.INSTANCE;
    }

    @Test
    void shouldConvertOneMapToTransactionDTO() {
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

        var source = new Source(transactionMap);

        var transactionDTO = mapper.mapToTransactionDTO(source);

        assertNotNull(transactionDTO);
        assertEquals(123L, transactionDTO.id());
        assertEquals("description", transactionDTO.description());
        assertEquals(BigDecimal.valueOf(1000.00), transactionDTO.price());
        assertEquals(1, transactionDTO.quantity());
        assertEquals(TransactionTypeEnum.CREDIT, transactionDTO.type());
        assertEquals(555, transactionDTO.originAccountNumber());
        assertEquals(444, transactionDTO.destinationAccountNumber());
        assertEquals(333L, transactionDTO.idempotencyId());
        assertEquals("12345678910", transactionDTO.creditCardNumber());
        assertEquals(222, transactionDTO.creditCardCvv());

    }
}