package br.net.silva.business.utils;

import br.net.silva.business.annotations.account.*;
import br.net.silva.business.annotations.account.Number;
import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.daniel.utils.ExtractMapUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class AccountMapperUtils {

    @Number
    public Integer number(Map<String, Object> map) {
        return Objects.requireNonNull(ExtractMapUtils.extractMapValue(map, TypeAccountMapperEnum.ACCOUNT.name(), "number", Long.class)).intValue();
    }

    @Agency
    public Integer agency(Map<String, Object> map) {
        return Objects.requireNonNull(ExtractMapUtils.extractMapValue(map, TypeAccountMapperEnum.ACCOUNT.name(), "agency", Long.class)).intValue();
    }

    @Balance
    public BigDecimal balance(Map<String, Object> map) {
        return BigDecimal.valueOf(ExtractMapUtils.extractMapValue(map, TypeAccountMapperEnum.ACCOUNT.name(), "balance", Long.class));
    }

    @Password
    public String password(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, TypeAccountMapperEnum.ACCOUNT.name(), "password", String.class);
    }

    @Active
    public Boolean active(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, TypeAccountMapperEnum.ACCOUNT.name(), "active", Boolean.class);
    }

    @Cpf
    public String cpf(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, TypeAccountMapperEnum.ACCOUNT.name(), "cpf", String.class);
    }
}
