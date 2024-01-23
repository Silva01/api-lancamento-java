package br.net.silva.business.value_object.input;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetInformationAccountInputTest {

    @Test
    public void testAccountNumber() {
        // Create an instance of GetInformationAccountInput
        GetInformationAccountInput input = new GetInformationAccountInput("00099988877");

        // Call the accountNumber method
        assertThrows(Exception.class, input::accountNumber);
    }

    @Test
    public void testBalance() {
        // Create an instance of GetInformationAccountInput
        GetInformationAccountInput input = new GetInformationAccountInput("00099988877");

        // Call the balance method
        assertThrows(Exception.class, input::balance);
    }

    @Test
    public void testPassword() {
        // Create an instance of GetInformationAccountInput
        GetInformationAccountInput input = new GetInformationAccountInput("00099988877");

        // Call the password method
        assertThrows(Exception.class, input::password);
    }

    @Test
    public void testActive() {
        // Create an instance of GetInformationAccountInput
        GetInformationAccountInput input = new GetInformationAccountInput("00099988877");

        // Call the active method
        assertThrows(Exception.class, input::active);
    }

    @Test
    public void testAgency() {
        // Create an instance of GetInformationAccountInput
        GetInformationAccountInput input = new GetInformationAccountInput("00099988877");

        // Call the agency method
        assertThrows(Exception.class, input::agency);
    }

}