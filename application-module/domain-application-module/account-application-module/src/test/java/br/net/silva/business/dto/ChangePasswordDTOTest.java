package br.net.silva.business.dto;

import br.net.silva.business.value_object.input.ChangePasswordDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ChangePasswordDTOTest {

    @Test
    void mustCreateDTOWithSuccess() {
        var change = new ChangePasswordDTO("44455566677", 1234, 23333, "123456", "432121");
        change.accept(ChangePasswordDTO.class);

        assertEquals("44455566677", change.cpf());
        assertEquals(1234, change.agency());
        assertEquals(23333, change.account());
        assertEquals("123456", change.password());
        assertEquals("432121", change.newPassword());
    }

    @Test
    void mustErrorCreateDTO() {
        var change = new ChangePasswordDTO("44455566677", 1234, 23333, "123456", "432121");
        var exceptionResponse = assertThrows(IllegalArgumentException.class, () -> change.accept(null));

        assertEquals("Type of class is incompatible with ChangePasswordDTO", exceptionResponse.getMessage());
    }

}