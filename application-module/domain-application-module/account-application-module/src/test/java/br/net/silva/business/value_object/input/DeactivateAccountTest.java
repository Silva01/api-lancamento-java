package br.net.silva.business.value_object.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeactivateAccountTest {

    @Test
    void testBalance() {
        // Create an instance of DeactivateAccount
        DeactivateAccount deactivateAccount = new DeactivateAccount("00099988877", 123, 456);

        // Call the balance method
        assertThrows(Exception.class, deactivateAccount::balance);
    }

    @Test
    void testPassword() {
        // Create an instance of DeactivateAccount
        DeactivateAccount deactivateAccount = new DeactivateAccount("00099988877", 123, 456);

        // Call the password method
        assertThrows(Exception.class, deactivateAccount::password);
    }

    @Test
    void testActive() {
        // Create an instance of DeactivateAccount
        DeactivateAccount deactivateAccount = new DeactivateAccount("00099988877", 123, 456);

        // Call the active method
        assertThrows(Exception.class, deactivateAccount::active);
    }

}