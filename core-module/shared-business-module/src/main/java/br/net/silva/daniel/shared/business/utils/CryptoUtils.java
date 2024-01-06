package br.net.silva.daniel.shared.business.utils;

import br.net.silva.daniel.shared.business.exception.ProblemConvertPasswordToCryptoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class CryptoUtils {

    private CryptoUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String convertToSHA256(String pass) {
        return convertTo(pass, "SHA-256");
    }

    public static String convertToMD5(String pass) {
        return convertTo(pass, "MD5");
    }

    public static String convertTo(String password, String algorithm) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new ProblemConvertPasswordToCryptoException(String.format("%s algorithm not found", algorithm), e);
        }


        byte[] hash = messageDigest.digest(password.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
