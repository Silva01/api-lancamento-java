package br.net.silva.daniel.utils;

import java.math.BigDecimal;
import java.util.Objects;

public class ValidateUtils {
    public static void balance(BigDecimal balance, BigDecimal value) {
        Objects.requireNonNull(balance, "Balance is null");
        Objects.requireNonNull(value, "Value is null");

        if (balance.compareTo(value) < 0) {
            throw new IllegalArgumentException("Balance is less than value");
        }
    }

    public static void isNotEmpty(String attribute, String messageError) {
        if (attribute.isEmpty()) {
            throw new IllegalArgumentException(messageError);
        }
    }

    public static void isNotNull(Object attribute, String messageError) {
        Objects.requireNonNull(attribute, messageError);
    }

    public static void isTextNotNullAndNotEmpty(String attribute, String messageError) {
        isNotNull(attribute, messageError);
        isNotEmpty(attribute, messageError);
    }

    public static void isLessThanZero(BigDecimal attribute, String messageError) {
        if (attribute.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(messageError);
        }
    }
}
