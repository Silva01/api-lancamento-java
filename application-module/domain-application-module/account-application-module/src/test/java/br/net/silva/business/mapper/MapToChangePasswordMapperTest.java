package br.net.silva.business.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MapToChangePasswordMapperTest {

    private MapToChangePasswordMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = MapToChangePasswordMapper.INSTANCE;
    }

    @Test
    void shouldConvertInputParamToChangePasswordDto() {
        Map<String, String> changePasswordMap = new HashMap<>();
        changePasswordMap.put("cpf", "12345678901");
        changePasswordMap.put("agency", "1234");
        changePasswordMap.put("account", "123456");
        changePasswordMap.put("password", "123456");
        changePasswordMap.put("newPassword", "654321");

        var changePasswordDto = mapper.mapToChangePasswordDto(changePasswordMap);
        assertNotNull(changePasswordDto);

        assertEquals("12345678901", changePasswordDto.cpf());
        assertEquals(1234, changePasswordDto.agency());
        assertEquals(123456, changePasswordDto.account());
        assertEquals("123456", changePasswordDto.password());
        assertEquals("654321", changePasswordDto.newPassword());
    }
}