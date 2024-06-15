package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.factory;

import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.AccountConfigValidationParam;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class AccountConfigFactory {

    public static AccountConfigValidationParam createAccountForValidation(Optional<Account> accountOpt) {
        return accountOpt
                .map(account -> new AccountConfigValidationParam(true, account.getBalance(), account.isActive()))
                .orElseGet(() -> new AccountConfigValidationParam(false, null, false));
    }
}
