package br.net.silva.business.dto;

import br.net.silva.business.value_object.input.FindAccountDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FindAccountDTOTest {

    @Test
    void mustCreateDTOWithSuccess() {
        var find = new FindAccountDTO("44455566677", 1234, 23333, "123456");
        find.accept(FindAccountDTO.class);

        assertEquals("44455566677", find.cpf());
        assertEquals(1234, find.agency());
        assertEquals(23333, find.account());
        assertEquals("123456", find.password());
    }

    @Test
    void mustErrorCreateDTO() {
        var find = new FindAccountDTO("44455566677", 1234, 23333, "123456");
        var exceptionResponse = assertThrows(IllegalArgumentException.class, () -> find.accept(null));

        assertEquals("Type of class is incompatible with FindAccountDTO", exceptionResponse.getMessage());
    }

}