package br.net.silva.daniel.utils;

import java.math.BigDecimal;
import java.util.Objects;

public class ValidateUtils {
    public static void balance(BigDecimal balance, BigDecimal value) {
        Objects.requireNonNull(balance, "Balance is null");
        Objects.requireNonNull(value, "Value is null");

        if (balance.compareTo(value) < 0) {
            executeException("Balance is less than value");
        }
    }

    public static void isNotEmpty(String attribute, String messageError) {
        if (attribute.isEmpty()) {
            executeException(messageError);
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
            executeException(messageError);
        }
    }

    public static void isEqualsZero(BigDecimal attribute, String messageError) {
        if (attribute.compareTo(BigDecimal.ZERO) == 0) {
            executeException(messageError);
        }
    }

    private static void executeException(String messageError) {
        throw new IllegalArgumentException(messageError);
    }
}
