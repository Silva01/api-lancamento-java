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

    public static void isEqualsZero(Object attribute, String messageError) {
        if (attribute instanceof BigDecimal attributeValue && (attributeValue.compareTo(BigDecimal.ZERO) == 0)) {
                executeException(messageError);

        }

        if (attribute instanceof Integer attributeValue && (attributeValue.equals(0))) {
                executeException(messageError);

        }

        if (attribute instanceof Long attributeValue && (attributeValue.equals(0L))) {
                executeException(messageError);

        }

        if (attribute instanceof Double attributeValue && (attributeValue.equals(0.0))) {
                executeException(messageError);

        }

    }

    private static void executeException(String messageError) {
        throw new IllegalArgumentException(messageError);
    }
}
