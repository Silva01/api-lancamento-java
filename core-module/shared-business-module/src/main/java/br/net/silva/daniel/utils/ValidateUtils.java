package br.net.silva.daniel.utils;

import java.math.BigDecimal;

public class ValidateUtils {
    public static void balance(BigDecimal balance, BigDecimal value) {
        if (balance == null) {
            throw new IllegalArgumentException("Balance is null");
        }

        if (balance.compareTo(value) < 0) {
            throw new IllegalArgumentException("Balance is less than value");
        }
    }
}
