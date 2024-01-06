package br.net.silva.daniel.utils;

import br.net.silva.daniel.shared.business.exception.ProblemConvertPasswordToCryptoException;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import junit.framework.TestCase;

public class CryptoUtilsTest extends TestCase {

    public void testMustCryptoPasswordsToSha256() {
        var password1 = "123890";
        var passwordCrypto1 = CryptoUtils.convertToSHA256(password1);

        assertNotNull(passwordCrypto1);

        var password2 = "123890";
        var passwordCrypto2 = CryptoUtils.convertToSHA256(password2);

        assertNotNull(passwordCrypto2);
        assertEquals(passwordCrypto1, passwordCrypto2);
    }

    public void testMustCryptoPasswordsToMD5() {
        var password1 = "123890";
        var passwordCrypto1 = CryptoUtils.convertToMD5(password1);

        assertNotNull(passwordCrypto1);

        var password2 = "123890";
        var passwordCrypto2 = CryptoUtils.convertToMD5(password2);

        assertNotNull(passwordCrypto2);
        assertEquals(passwordCrypto1, passwordCrypto2);
    }

    public void testMustErrorConvertToSHA256() {
        try {
            var password1 = "Teste";
            var passwordCrypto1 = CryptoUtils.convertTo(password1, "lalalala");
        } catch (ProblemConvertPasswordToCryptoException e) {
            assertEquals("lalalala algorithm not found", e.getMessage());
        }

    }

}