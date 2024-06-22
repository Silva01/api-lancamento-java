package br.net.silva.business.value_object.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivateAccountTest {

    @Test
    public void testBalance() {
        // Create an instance of ActivateAccount
        ActivateAccount activateAccount = new ActivateAccount(123, 456, "00099988877");

        // Call the balance method
        assertThrows(Exception.class, activateAccount::balance);
    }

    @Test
    public void testPassword() {
        // Create an instance of ActivateAccount
        ActivateAccount activateAccount = new ActivateAccount(123, 456, "00099988877");

        // Call the password method
        assertThrows(Exception.class, activateAccount::password);
    }

    @Test
    public void testActive() {
        // Create an instance of ActivateAccount
        ActivateAccount activateAccount = new ActivateAccount(123, 456, "00099988877");

        // Call the active method
        assertThrows(Exception.class, activateAccount::active);
    }

}