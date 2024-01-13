package br.net.silva.business.utils;

import br.net.silva.business.annotations.*;
import br.net.silva.business.annotations.Number;
import br.net.silva.daniel.utils.ExtractMapUtils;

import java.math.BigDecimal;
import java.util.Map;

public class AccountMapperUtils {

    private static final String DOMAIN_KEY_ACCOUNT = "account";

    @Number
    public Integer number(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, DOMAIN_KEY_ACCOUNT, "number", Integer.class);
    }

    @Agency
    public Integer agency(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, DOMAIN_KEY_ACCOUNT, "agency", Integer.class);
    }

    @Balance
    public BigDecimal balance(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, DOMAIN_KEY_ACCOUNT, "balance", BigDecimal.class);
    }

    @Password
    public String password(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, DOMAIN_KEY_ACCOUNT, "password", String.class);
    }

    @Active
    public Boolean active(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, DOMAIN_KEY_ACCOUNT, "active", Boolean.class);
    }

    @Cpf
    public String cpf(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, DOMAIN_KEY_ACCOUNT, "cpf", String.class);
    }
}
