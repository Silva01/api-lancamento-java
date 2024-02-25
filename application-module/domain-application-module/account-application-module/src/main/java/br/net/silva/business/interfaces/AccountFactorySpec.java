package br.net.silva.business.interfaces;

import br.net.silva.business.value_object.output.CreditCardOutput;
import br.net.silva.business.value_object.output.TransactionOutput;

import java.math.BigDecimal;
import java.util.List;

public interface AccountFactorySpec {

    interface NumberSpec<R> {
        AccountFactorySpec.AgencySpec<R> withNumber(Integer number);
    }

    interface AgencySpec<R> {
        BalanceSpec<R> withAgency(Integer agency);
    }

    interface BalanceSpec<R> {
        PasswordSpec<R> withBalance(BigDecimal balance);
    }

    interface PasswordSpec<R> {
        ActiveSpec<R> withPassword(String password);
    }

    interface ActiveSpec<R> {
        CpfSpec<R> withFlagActive(Boolean active);
    }

    interface CpfSpec<R> {
        TransactionsSpec<R> withCpf(String cpf);
        BuildSpec<R> andWithCpf(String cpf);
    }

    interface TransactionsSpec<R> {
        CreditCardSpec<R> withTransactions(List<TransactionOutput> transactions);
        BuildSpec<R> andWithTransactions(List<TransactionOutput> transactions);
    }

    interface CreditCardSpec<R> {
        BuildSpec<R> andWithCreditCard(CreditCardOutput creditCard);
    }

    interface BuildSpec<R> extends NumberSpec<R>, AgencySpec<R>, BalanceSpec<R>, PasswordSpec<R>, ActiveSpec<R>, CpfSpec<R>, TransactionsSpec<R>, CreditCardSpec<R> {
        R build();
    }
}
