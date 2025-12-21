package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.factory;

import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.aggregate.BaseAccountAggregate;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.DestinyAccountAggregate;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.SourceAccountAggregate;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class ValidatorFactory {

    public static BaseAccountAggregate createValidatorConfiguratorForSourceAccount(BatchTransactionInput message, Optional<Account> accountOpt) throws DomainGenericException {
        return createValidator(message.sourceAccount(), message.calculateTotal(), accountOpt, SourceAccountAggregate.class, message.hasTransactionDuplicated());
    }

    public static BaseAccountAggregate createValidatorConfiguratorForDestinyAccount(BatchTransactionInput message, Optional<Account> accountOpt) throws DomainGenericException {
        return createValidator(message.destinyAccount(), message.calculateTotal(), accountOpt, DestinyAccountAggregate.class, false);
    }

    private static BaseAccountAggregate createValidator(AccountInput accountInput, BigDecimal totalTransaction, Optional<Account> accountOpt, Class<? extends BaseAccountAggregate> clazz, boolean hasDuplicatedTransaction) throws DomainGenericException {
        try {
            return (BaseAccountAggregate) clazz.getConstructors()[0].newInstance(
                    accountInput,
                    totalTransaction,
                    AccountConfigFactory.createAccountForValidation(accountOpt, hasDuplicatedTransaction));
        } catch (Exception e) {
            throw new DomainGenericException(e.getMessage());
        }
    }
}
