package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.validation;

import br.net.silva.business.exception.AccountBalanceInsufficientExceptionDomain;
import br.net.silva.business.exception.AccountDeactivatedExceptionDomain;
import br.net.silva.business.exception.AccountNotExistsExceptionDomain;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;

import java.math.BigDecimal;
import java.util.Optional;

public class Validation {

    public static AccountValidationSpec buildValidation(BigDecimal totalTransaction, String messageError) {
        return new AccountValidationSpec(totalTransaction, messageError);
    }


    public interface ValidationAccount {
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        ValidationAccount validateIfAccountExists(Optional<Account> accountOptional) throws DomainGenericException;
        ValidationAccount validateIfAccountIsActive() throws DomainGenericException;
        void validateIfBalanceIsSufficient() throws DomainGenericException;
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
        public ValidationAccount validateIfAccountExists(Optional<Account> accountOptional) throws DomainGenericException {
            this.account = accountOptional
                    .orElseThrow(() -> new AccountNotExistsExceptionDomain(buildMessageError("Account not found")));
            return this;
        }

        @Override
        public ValidationAccount validateIfAccountIsActive() throws DomainGenericException {
            if (!this.account.isActive()) {
                throw new AccountDeactivatedExceptionDomain(buildMessageError("Account is not active"));
            }

            return this;
        }

        @Override
        public void validateIfBalanceIsSufficient() throws DomainGenericException {
            if (this.account.getBalance().compareTo(this.totalTransaction) < 0) {
                throw new AccountBalanceInsufficientExceptionDomain(buildMessageError("Insufficient balance"));
            }
        }

        public String buildMessageError(String error) {
            return String.format("%s %s", this.messageError, error);
        }
    }
}
