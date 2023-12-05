package br.net.silva.daniel.utils;

import java.math.BigDecimal;
import java.util.Objects;

public class ValidateUtils {
    public static void balance(BigDecimal balance, BigDecimal value) {
        Objects.requireNonNull(balance, "Balance is null");
        Objects.requireNonNull(value, "Value is null");

        if (balance.compareTo(value) < 0) {
            throw GenericErrorUtils.executeException("Balance is less than value");
        }
    }

    public static void isNotEmpty(String attribute, String messageError) {
        if (attribute.isEmpty()) {
            throw GenericErrorUtils.executeException(messageError);
        }
    }

    public static void isNotNull(Object attribute, String messageError) {
        Objects.requireNonNull(attribute, messageError);
    }

    public static void isTextNotNullAndNotEmpty(String attribute, String messageError) {
        isNotNull(attribute, messageError);
        isNotEmpty(attribute, messageError);
    }

    public static void isLessThanZero(Object attribute, String messageError) {
        if (attribute instanceof BigDecimal attributeValue && (attributeValue.compareTo(BigDecimal.ZERO) < 0)) {
            throw GenericErrorUtils.executeException(messageError);
        }

        if (attribute instanceof Integer attributeValue && (attributeValue < 0)) {
            throw GenericErrorUtils.executeException(messageError);
        }

        if (attribute instanceof Long attributeValue && (attributeValue < 0L)) {
            throw GenericErrorUtils.executeException(messageError);
        }

        if (attribute instanceof Double attributeValue && (attributeValue < 0.0)) {
            throw GenericErrorUtils.executeException(messageError);
        }
    }

    public static void isEqualsZero(Object attribute, String messageError) {
        if (attribute instanceof BigDecimal attributeValue && (attributeValue.compareTo(BigDecimal.ZERO) == 0)) {
            throw GenericErrorUtils.executeException(messageError);
        }

        if (attribute instanceof Integer attributeValue && (attributeValue.equals(0))) {
            throw GenericErrorUtils.executeException(messageError);
        }

        if (attribute instanceof Long attributeValue && (attributeValue.equals(0L))) {
            throw GenericErrorUtils.executeException(messageError);
        }

        if (attribute instanceof Double attributeValue && (attributeValue.equals(0.0))) {
            throw GenericErrorUtils.executeException(messageError);
        }
    }
}
