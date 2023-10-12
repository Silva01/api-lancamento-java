package br.net.silva.daniel.validation;

import java.math.BigDecimal;
import java.util.Objects;

public interface Validation {
    void validate();

    default void validateAttributeNonNull(Object attribute, String messageError) {
        Objects.requireNonNull(attribute, messageError);
    }

    default void validateAttributeNotEmpty(String attribute, String messageError) {
        if (attribute.isEmpty()) {
            throw new IllegalArgumentException(messageError);
        }
    }
    default void validateAttributeLessThanZero(BigDecimal attribute, String messageError) {
        if (attribute.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(messageError);
        }
    }
}
