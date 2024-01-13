package br.net.silva.business.utils;

import br.net.silva.business.annotations.creditcard.*;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.utils.ExtractMapUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class CreditCardMapperUtils {

    @CreditCardNumber
    public String number(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "number", String.class);
    }

    @CreditCardCvv
    public Integer cvv(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "cvv", Integer.class);
    }

    @CreditCardBalance
    public BigDecimal balance(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "balance", BigDecimal.class);
    }

    @CreditCardExpirationDate
    public LocalDate expirationDate(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "expirationDate", LocalDate.class);
    }

    @CreditCardActive
    public Boolean active(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "active", Boolean.class);
    }

    @CreditCardFlag
    public FlagEnum flag(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "flag", FlagEnum.class);
    }
}
