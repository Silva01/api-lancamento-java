package br.net.silva.business.utils;

import br.net.silva.business.exception.ProblemConvertPasswordToCryptoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class CryptoUtils {

    private CryptoUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String convertToSHA256(String pass) {
        try {
            return convertTo(pass, "SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new ProblemConvertPasswordToCryptoException("SHA-256 algorithm not found", e);
        }
    }

    public static String convertToMD5(String pass) {
        try {
            return convertTo(pass, "MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new ProblemConvertPasswordToCryptoException("SHA-256 algorithm not found", e);
        }
    }

    private static String convertTo(String password, String algorithm) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
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
