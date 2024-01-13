package br.net.silva.business.utils;

import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.mapper.MapToAccountMapper;
import br.net.silva.business.mapper.MapToAccountMapperImpl;
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
        this.mapper = new MapToAccountMapperImpl();
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

        Map<String, Object> accountMap = new HashMap<>();
        accountMap.put(TypeAccountMapperEnum.TRANSACTION.name(), transactionMap);

        Map<String, Object> principalMap = new HashMap<>();
        principalMap.put(TypeAccountMapperEnum.ACCOUNT.name(), accountMap);

        var source = new Source(principalMap);

        var transactionDTO = mapper.mapToTransactionDTO(source);

        assertNotNull(transactionDTO);

    }

}