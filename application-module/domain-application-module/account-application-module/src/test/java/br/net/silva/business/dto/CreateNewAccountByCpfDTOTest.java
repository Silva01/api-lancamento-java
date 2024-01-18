package br.net.silva.business.dto;

import br.net.silva.business.value_object.input.CreateNewAccountByCpfDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateNewAccountByCpfDTOTest {

    @Test
    void mustCreateDTOWithSuccess() {
        var create = new CreateNewAccountByCpfDTO("44455566677", 1234, "123456");
        create.accept(CreateNewAccountByCpfDTO.class);

        assertEquals("44455566677", create.cpf());
        assertEquals(1234, create.agency());
        assertEquals("123456", create.password());
    }

    @Test
    void mustErrorCreateDTO() {
        var create = new CreateNewAccountByCpfDTO("44455566677", 1234, "123456");
        var exceptionResponse = assertThrows(IllegalArgumentException.class, () -> create.accept(null));

        assertEquals("Type of class is not CreateNewAccountByCpfDTO", exceptionResponse.getMessage());
    }
}