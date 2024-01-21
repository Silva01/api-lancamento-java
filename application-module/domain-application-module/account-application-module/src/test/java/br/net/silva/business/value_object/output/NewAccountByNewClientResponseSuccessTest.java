package br.net.silva.business.value_object.output;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewAccountByNewClientResponseSuccessTest {

    @Test
    public void testEquals() {
        NewAccountByNewClientResponseSuccess obj1 = new NewAccountByNewClientResponseSuccess();
        obj1.setAgency(123);
        obj1.setAccountNumber(456);
        obj1.setProvisionalPassword("password1");

        NewAccountByNewClientResponseSuccess obj2 = new NewAccountByNewClientResponseSuccess();
        obj2.setAgency(123);
        obj2.setAccountNumber(456);
        obj2.setProvisionalPassword("password1");

        assertEquals(obj1, obj2);
    }

    @Test
    public void testHashCode() {
        NewAccountByNewClientResponseSuccess obj1 = new NewAccountByNewClientResponseSuccess();
        obj1.setAgency(123);
        obj1.setAccountNumber(456);
        obj1.setProvisionalPassword("password1");

        NewAccountByNewClientResponseSuccess obj2 = new NewAccountByNewClientResponseSuccess();
        obj2.setAgency(123);
        obj2.setAccountNumber(456);
        obj2.setProvisionalPassword("password1");

        assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    @Test
    public void testToString() {
        NewAccountByNewClientResponseSuccess obj = new NewAccountByNewClientResponseSuccess();
        obj.setAgency(123);
        obj.setAccountNumber(456);
        obj.setProvisionalPassword("password1");

        String expectedToString = "NewAccountByNewClientResponseSuccess(agency=123, accountNumber=456, provisionalPassword=password1)";
        assertEquals(expectedToString, obj.toString());
    }

    @Test
    public void testCanEqual() {
        NewAccountByNewClientResponseSuccess obj1 = new NewAccountByNewClientResponseSuccess();
        obj1.setAgency(123);
        obj1.setAccountNumber(456);
        obj1.setProvisionalPassword("password1");

        NewAccountByNewClientResponseSuccess obj2 = new NewAccountByNewClientResponseSuccess();
        obj2.setAgency(123);
        obj2.setAccountNumber(456);
        obj2.setProvisionalPassword("password1");

        assertTrue(obj1.canEqual(obj2));
    }

}