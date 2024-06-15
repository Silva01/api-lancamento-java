package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.factory;

import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.enums.AccountType;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.ValidatorConfigurator;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ValidatorFactory {

    public static ValidatorConfigurator createValidatorConfiguratorForSourceAccount(BatchTransactionInput message, Optional<Account> accountOpt) {
        return createValidator(message.sourceAccount(), message.calculateTotal(), accountOpt, AccountType.SOURCE);
    }

    public static ValidatorConfigurator createValidatorConfiguratorForDestinyAccount(BatchTransactionInput message, Optional<Account> accountOpt) {
        return createValidator(message.destinyAccount(), message.calculateTotal(), accountOpt, AccountType.DESTINY);
    }

    private static ValidatorConfigurator createValidator(AccountInput accountInput, BigDecimal totalTransaction, Optional<Account> accountOpt, AccountType accountType) {
        return new ValidatorConfigurator(
                accountInput,
                accountType,
                totalTransaction,
                AccountConfigFactory.createAccountForValidation(accountOpt));
    }
}
