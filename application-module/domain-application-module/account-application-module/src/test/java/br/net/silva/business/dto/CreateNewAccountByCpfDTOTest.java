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

    @Test
    public void testAcceptMethod() {
        // Create an instance of CreateNewAccountByCpfDTO
        CreateNewAccountByCpfDTO dto = new CreateNewAccountByCpfDTO("00099988877", 1234, "secretpassword");

        // Call the accept method
        dto.accept(CreateNewAccountByCpfDTO.class);

        // No exception should be thrown if the accept method works as expected
    }

    @Test
    public void testGet() {
        // Create an instance of CreateNewAccountByCpfDTO
        CreateNewAccountByCpfDTO dto = new CreateNewAccountByCpfDTO("00099988877", 1234, "secretpassword");

        // Call the get method
        Object result = dto.get();

        // Validate that the result is the same instance
        assertEquals(dto, result);
    }
}