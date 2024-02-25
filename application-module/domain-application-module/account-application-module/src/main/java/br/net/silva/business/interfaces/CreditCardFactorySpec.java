package br.net.silva.business.interfaces;

import br.net.silva.daniel.enuns.FlagEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CreditCardFactorySpec {

    interface NumberSpec<R> {
        CvvSpec<R> withNumber(String number);
    }

    interface CvvSpec<R> {
        CreditCardBrandSpec<R> withCvv(Integer cvv);
    }

    interface CreditCardBrandSpec<R> {
        BalanceSpec<R> withCreditCardBrand(FlagEnum flag);
    }

    interface BalanceSpec<R> {
        ExpirationDateSpec<R> withBalance(BigDecimal balance);
    }

    interface ExpirationDateSpec<R> {
        ActiveSpec<R> withExpirationDate(LocalDate expirationDate);
    }

    interface ActiveSpec<R> {
        BuildSpec<R> andWithFlagActive(boolean active);
    }

    interface BuildSpec<R> extends NumberSpec<R>, CvvSpec<R>, CreditCardBrandSpec<R>, BalanceSpec<R>, ExpirationDateSpec<R>, ActiveSpec<R>{
        R build();
    }

}
