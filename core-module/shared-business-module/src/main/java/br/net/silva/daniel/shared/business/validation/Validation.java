package br.net.silva.daniel.shared.business.validation;

import br.net.silva.daniel.shared.business.utils.ValidateUtils;

import java.math.BigDecimal;

public abstract class Validation {
    public abstract void validate();

    public void validateAttributeNonNull(Object attribute, String messageError) {
        ValidateUtils.isNotNull(attribute, messageError);
    }

    public void validateAttributeNotEmpty(String attribute, String messageError) {
        ValidateUtils.isNotEmpty(attribute, messageError);
    }
    public void validateAttributeNotNullAndNotEmpty(String attribute, String messageError) {
        ValidateUtils.isTextNotNullAndNotEmpty(attribute, messageError);
    }
    public void validateAttributeLessThanZero(Object attribute, String messageError) {
        ValidateUtils.isLessThanZero(attribute, messageError);
    }

    public void validateBalance(BigDecimal balance, BigDecimal value) {
        ValidateUtils.balance(balance, value);
    }

    public void validateAttributeEqualsZero(Object attribute, String messageError) {
        ValidateUtils.isEqualsZero(attribute, messageError);
    }
}
