package br.net.silva.business.value_object.output;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewAccountResponseTest {

    @Test
    public void testEquals() {
        NewAccountResponse obj1 = new NewAccountResponse();
        obj1.setAgency(123);
        obj1.setAccountNumber(456);

        NewAccountResponse obj2 = new NewAccountResponse();
        obj2.setAgency(123);
        obj2.setAccountNumber(456);

        assertEquals(obj1, obj2);
    }

    @Test
    public void testHashCode() {
        NewAccountResponse obj1 = new NewAccountResponse();
        obj1.setAgency(123);
        obj1.setAccountNumber(456);

        NewAccountResponse obj2 = new NewAccountResponse();
        obj2.setAgency(123);
        obj2.setAccountNumber(456);

        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void testToString() {
        NewAccountResponse obj = new NewAccountResponse();
        obj.setAgency(123);
        obj.setAccountNumber(456);

        String expectedToString = "NewAccountResponse(agency=123, accountNumber=456)";
        assertEquals(expectedToString, obj.toString());
    }

    @Test
    public void testCanEqual() {
        NewAccountResponse obj1 = new NewAccountResponse();
        obj1.setAgency(123);
        obj1.setAccountNumber(456);

        NewAccountResponse obj2 = new NewAccountResponse();
        obj2.setAgency(123);
        obj2.setAccountNumber(456);

        assertTrue(obj1.equals(obj2));
    }

}