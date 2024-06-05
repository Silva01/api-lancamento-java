package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.validation;

import br.net.silva.business.exception.AccountBalanceInsufficientException;
import br.net.silva.business.exception.AccountDeactivatedException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;

import java.math.BigDecimal;
import java.util.Optional;

public class Validation {

    public static AccountValidationSpec buildValidation(BigDecimal totalTransaction, String messageError) {
        return new AccountValidationSpec(totalTransaction, messageError);
    }


    public interface ValidationAccount {
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        ValidationAccount validateIfAccountExists(Optional<Account> accountOptional) throws GenericException;
        ValidationAccount validateIfAccountIsActive() throws GenericException;
        void validateIfBalanceIsSufficient() throws GenericException;
    }

    public static class AccountValidationSpec implements ValidationAccount {

        private Account account;
        private final BigDecimal totalTransaction;
        private final String messageError;

        public AccountValidationSpec(BigDecimal totalTransaction, String messageError) {
            this.totalTransaction = totalTransaction;
            this.messageError = messageError;
        }

        @Override
        public ValidationAccount validateIfAccountExists(Optional<Account> accountOptional) throws GenericException {
            this.account = accountOptional
                    .orElseThrow(() -> new AccountNotExistsException(buildMessageError("Account not found")));
            return this;
        }

        @Override
        public ValidationAccount validateIfAccountIsActive() throws GenericException {
            if (!this.account.isActive()) {
                throw new AccountDeactivatedException(buildMessageError("Account is not active"));
            }

            return this;
        }

        @Override
        public void validateIfBalanceIsSufficient() throws GenericException {
            if (this.account.getBalance().compareTo(this.totalTransaction) < 0) {
                throw new AccountBalanceInsufficientException(buildMessageError("Insufficient balance"));
            }
        }

        public String buildMessageError(String error) {
            return String.format("%s %s", this.messageError, error);
        }
    }
}
