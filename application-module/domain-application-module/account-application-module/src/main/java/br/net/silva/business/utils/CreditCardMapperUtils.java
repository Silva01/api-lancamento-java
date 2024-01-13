package br.net.silva.business.utils;

import br.net.silva.business.annotations.creditcard.*;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.utils.ExtractMapUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class CreditCardMapperUtils {

    private static final String DOMAIN_KEY_ACCOUNT = "account";
    private static final String SUB_DOMAIN_KEY_ACCOUNT = "creditCard";

    @CreditCardNumber
    public String number(Map<String, Object> map) {
        var subDomainMap = ExtractMapUtils.extractMapValue(map, DOMAIN_KEY_ACCOUNT, SUB_DOMAIN_KEY_ACCOUNT, Map.class);
        return ExtractMapUtils.extractMapValue(subDomainMap, "number", String.class);
    }

    @CreditCardCvv
    public Integer cvv(Map<String, Object> map) {
        var subDomainMap = ExtractMapUtils.extractMapValue(map, DOMAIN_KEY_ACCOUNT, SUB_DOMAIN_KEY_ACCOUNT, Map.class);
        return ExtractMapUtils.extractMapValue(subDomainMap, "cvv", Integer.class);
    }

    @CreditCardBalance
    public BigDecimal balance(Map<String, Object> map) {
        var subDomainMap = ExtractMapUtils.extractMapValue(map, DOMAIN_KEY_ACCOUNT, SUB_DOMAIN_KEY_ACCOUNT, Map.class);
        return ExtractMapUtils.extractMapValue(subDomainMap, "balance", BigDecimal.class);
    }

    @CreditCardExpirationDate
    public LocalDate expirationDate(Map<String, Object> map) {
        var subDomainMap = ExtractMapUtils.extractMapValue(map, DOMAIN_KEY_ACCOUNT, SUB_DOMAIN_KEY_ACCOUNT, Map.class);
        return ExtractMapUtils.extractMapValue(subDomainMap, "expirationDate", LocalDate.class);
    }

    @CreditCardActive
    public Boolean active(Map<String, Object> map) {
        var subDomainMap = ExtractMapUtils.extractMapValue(map, DOMAIN_KEY_ACCOUNT, SUB_DOMAIN_KEY_ACCOUNT, Map.class);
        return ExtractMapUtils.extractMapValue(subDomainMap, "active", Boolean.class);
    }

    @CreditCardFlag
    public FlagEnum flag(Map<String, Object> map) {
        var subDomainMap = ExtractMapUtils.extractMapValue(map, DOMAIN_KEY_ACCOUNT, SUB_DOMAIN_KEY_ACCOUNT, Map.class);
        return ExtractMapUtils.extractMapValue(subDomainMap, "flag", FlagEnum.class);
    }
}
