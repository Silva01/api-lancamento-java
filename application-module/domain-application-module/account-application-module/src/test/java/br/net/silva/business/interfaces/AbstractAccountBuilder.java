package br.net.silva.business.interfaces;

import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.CreditCard;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

public abstract class AbstractAccountBuilder {

    protected Account buildMockAccount(boolean active, CreditCard creditCard) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", creditCard, Collections.emptyList());
    }

    protected CreditCard buildMockCreditCard(boolean activate) {
        return new CreditCard("99988877766", 45678, FlagEnum.MASTER_CARD, BigDecimal.valueOf(1000), LocalDate.of(2027, 1, 1), activate);
    }
}
