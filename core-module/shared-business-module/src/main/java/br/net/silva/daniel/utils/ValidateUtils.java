package br.net.silva.daniel.utils;

import java.math.BigDecimal;
import java.util.Objects;

public class ValidateUtils {
    public static void balance(BigDecimal balance, BigDecimal value) {
        Objects.requireNonNull(balance, "Balance is null");
        Objects.requireNonNull(value, "Value is null");

        if (balance.compareTo(value) < 0) {
            throw new IllegalArgumentException("Balance is less than value");
        }
    }
}
