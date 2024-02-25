package br.net.silva.business.factory;

import br.net.silva.business.interfaces.CreditCardFactorySpec;
import br.net.silva.business.value_object.output.CreditCardOutput;
import br.net.silva.daniel.enuns.FlagEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditCardOutputFactory implements CreditCardFactorySpec.BuildSpec<CreditCardOutput> {

    private String number;
    private Integer cvv;
    private FlagEnum flag;
    private BigDecimal balance;
    private LocalDate expirationDate;
    private boolean active;

    public static CreditCardFactorySpec.NumberSpec<CreditCardOutput> createOutput() {
        return new CreditCardOutputFactory();
    }

    @Override
    public CreditCardFactorySpec.CvvSpec<CreditCardOutput> withNumber(String number) {
        this.number = number;
        return this;
    }

    @Override
    public CreditCardFactorySpec.CreditCardBrandSpec<CreditCardOutput> withCvv(Integer cvv) {
        this.cvv = cvv;
        return this;
    }

    @Override
    public CreditCardFactorySpec.BalanceSpec<CreditCardOutput> withCreditCardBrand(FlagEnum flag) {
        this.flag = flag;
        return this;
    }

    @Override
    public CreditCardFactorySpec.ExpirationDateSpec<CreditCardOutput> withBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    @Override
    public CreditCardFactorySpec.ActiveSpec<CreditCardOutput> withExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    @Override
    public CreditCardFactorySpec.BuildSpec<CreditCardOutput> andWithFlagActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public CreditCardOutput build() {
        return new CreditCardOutput(number, cvv, flag, balance, expirationDate, active);
    }
}
