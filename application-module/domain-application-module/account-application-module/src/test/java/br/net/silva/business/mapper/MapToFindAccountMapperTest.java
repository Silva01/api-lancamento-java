package br.net.silva.business.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapToFindAccountMapperTest {

    private MapToFindAccountMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new MapToFindAccountMapperImpl();
    }

    @Test
    void shouldConvertInputParamToFindAccountDto() {
        Map<String, String> findAccountMap = new HashMap<>();
        findAccountMap.put("cpf", "12345678901");
        findAccountMap.put("agency", "1234");
        findAccountMap.put("account", "123456");
        findAccountMap.put("password", "123456");

        var findAccountDto = mapper.mapToFindAccountDto(findAccountMap);
        assertNotNull(findAccountDto);

        assertEquals("12345678901", findAccountDto.cpf());
        assertEquals(1234, findAccountDto.agency());
        assertEquals(123456, findAccountDto.account());
        assertEquals("123456", findAccountDto.password());
    }

}