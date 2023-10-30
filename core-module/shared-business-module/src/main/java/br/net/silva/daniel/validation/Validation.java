package br.net.silva.daniel.validation;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Validation {
    protected abstract void validate();

    public void validateAttributeNonNull(Object attribute, String messageError) {
        Objects.requireNonNull(attribute, messageError);
    }

    public void validateAttributeNotEmpty(String attribute, String messageError) {
        if (attribute.isEmpty()) {
            throw new IllegalArgumentException(messageError);
        }
    }
    public void validateAttributeNotNullAndNotEmpty(String attribute, String messageError) {
        if (attribute == null || attribute.isEmpty()) {
            throw new IllegalArgumentException(messageError);
        }
    }
    public void validateAttributeLessThanZero(BigDecimal attribute, String messageError) {
        if (attribute.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(messageError);
        }
    }
}
