package br.net.silva.business.value_object.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChangePasswordDTOTest {

    @Test
    public void testChangePasswordDTO() {
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("123456789", 1234, 5678, "oldPassword", "newPassword");

        assertEquals("123456789", changePasswordDTO.cpf());
        assertEquals(1234, changePasswordDTO.agency());
        assertEquals(5678, changePasswordDTO.account());
        assertEquals("oldPassword", changePasswordDTO.password());
        assertEquals("newPassword", changePasswordDTO.newPassword());

        assertThrows(Exception.class, () -> changePasswordDTO.balance());
        assertThrows(Exception.class, () -> changePasswordDTO.active());
    }
}