package br.net.silva.daniel.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CryptoUtilsTest {

    @Test
    void mustCryptoPasswordsToSha256() {
        var password1 = "123890";
        var passwordCrypto1 = CryptoUtils.convertToSHA256(password1);

        assertNotNull(passwordCrypto1);

        var password2 = "123890";
        var passwordCrypto2 = CryptoUtils.convertToSHA256(password2);

        assertNotNull(passwordCrypto2);
        assertEquals(passwordCrypto1, passwordCrypto2);
    }

    @Test
    void mustCryptoPasswordsToMD5() {
        var password1 = "123890";
        var passwordCrypto1 = CryptoUtils.convertToMD5(password1);

        assertNotNull(passwordCrypto1);

        var password2 = "123890";
        var passwordCrypto2 = CryptoUtils.convertToMD5(password2);

        assertNotNull(passwordCrypto2);
        assertEquals(passwordCrypto1, passwordCrypto2);
    }

}