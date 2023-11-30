package br.net.silva.daniel.validation;

import br.net.silva.daniel.utils.ValidateUtils;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class Validation {
    public abstract void validate();

    protected void validateAttributeNonNull(Object attribute, String messageError) {
        ValidateUtils.isNotNull(attribute, messageError);
    }

    protected void validateAttributeNotEmpty(String attribute, String messageError) {
        ValidateUtils.isNotEmpty(attribute, messageError);
    }
    protected void validateAttributeNotNullAndNotEmpty(String attribute, String messageError) {
        ValidateUtils.isTextNotNullAndNotEmpty(attribute, messageError);
    }
    protected void validateAttributeLessThanZero(Object attribute, String messageError) {
        ValidateUtils.isLessThanZero(attribute, messageError);
    }

    protected void validateBalance(BigDecimal balance, BigDecimal value) {
        ValidateUtils.balance(balance, value);
    }

    protected void validateAttributeEqualsZero(Object attribute, String messageError) {
        ValidateUtils.isEqualsZero(attribute, messageError);
    }
}
