package br.net.silva.daniel.validation;

import br.net.silva.daniel.utils.ValidateUtils;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Validation {
    protected abstract void validate();

    public void validateAttributeNonNull(Object attribute, String messageError) {
        ValidateUtils.isNotNull(attribute, messageError);
    }

    public void validateAttributeNotEmpty(String attribute, String messageError) {
        ValidateUtils.isNotEmpty(attribute, messageError);
    }
    public void validateAttributeNotNullAndNotEmpty(String attribute, String messageError) {
        ValidateUtils.isTextNotNullAndNotEmpty(attribute, messageError);
    }
    public void validateAttributeLessThanZero(BigDecimal attribute, String messageError) {
        ValidateUtils.isLessThanZero(attribute, messageError);
    }
}
