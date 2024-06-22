package br.net.silva.business.factory;

import br.net.silva.business.interfaces.CreditCardFactorySpec;
import br.net.silva.daniel.dto.CreditCardDTO;
import br.net.silva.daniel.enuns.FlagEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditCardDtoFactory implements CreditCardFactorySpec.BuildSpec<CreditCardDTO> {

    private String number;
    private Integer cvv;
    private FlagEnum flag;
    private BigDecimal balance;
    private LocalDate expirationDate;
    private boolean active;

    public static CreditCardFactorySpec.NumberSpec<CreditCardDTO> createDto() {
        return new CreditCardDtoFactory();
    }

    @Override
    public CreditCardFactorySpec.CvvSpec<CreditCardDTO> withNumber(String number) {
        this.number = number;
        return this;
    }

    @Override
    public CreditCardFactorySpec.CreditCardBrandSpec<CreditCardDTO> withCvv(Integer cvv) {
        this.cvv = cvv;
        return this;
    }

    @Override
    public CreditCardFactorySpec.BalanceSpec<CreditCardDTO> withCreditCardBrand(FlagEnum flag) {
        this.flag = flag;
        return this;
    }

    @Override
    public CreditCardFactorySpec.ExpirationDateSpec<CreditCardDTO> withBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    @Override
    public CreditCardFactorySpec.ActiveSpec<CreditCardDTO> withExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    @Override
    public CreditCardFactorySpec.BuildSpec<CreditCardDTO> andWithFlagActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public CreditCardDTO build() {
        return new CreditCardDTO(number, cvv, flag, balance, expirationDate, active);
    }
}
