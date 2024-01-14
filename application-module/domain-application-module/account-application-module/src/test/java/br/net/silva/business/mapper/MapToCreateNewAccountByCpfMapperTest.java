package br.net.silva.business.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MapToCreateNewAccountByCpfMapperTest {

    private MapToCreateNewAccountByCpfMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = MapToCreateNewAccountByCpfMapper.INSTANCE;
    }

    @Test
    void mapConvertMapToCreateNewAccountByCpfDto() {
        Map<String, String> map = new HashMap<>();
        map.put("cpf", "12345678900");
        map.put("agency", "1234");
        map.put("password", "543216");

        var dto = mapper.mapToCreateNewAccountByCpfDto(map);
        assertNotNull(dto);
        assertEquals("12345678900", dto.cpf());
        assertEquals(1234, dto.agency());
        assertEquals("543216", dto.password());
    }

}