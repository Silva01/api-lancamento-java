package br.net.silva.daniel.factory;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.CreditCard;
import br.net.silva.daniel.entity.Transaction;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateAccountByAccountDTOFactory implements IFactoryAggregate<Account, AccountDTO> {
    @Override
    public Account create(AccountDTO accountDTO) {
        CreditCard creditCardAggregate = null;
        List<Transaction> transactions = new ArrayList<>();
        if (Objects.nonNull(accountDTO.creditCard())) {
            creditCardAggregate = new CreditCard(
                    accountDTO.creditCard().number(),
                    accountDTO.creditCard().cvv(),
                    accountDTO.creditCard().flag(),
                    accountDTO.creditCard().balance(),
                    accountDTO.creditCard().expirationDate(),
                    accountDTO.creditCard().active());
        }

        if (Objects.nonNull(accountDTO.transactions()) && !accountDTO.transactions().isEmpty()) {
            transactions = accountDTO.transactions().stream().map(transactionDTO -> new Transaction(
                    transactionDTO.id(),
                    transactionDTO.description(),
                    transactionDTO.price(),
                    transactionDTO.quantity(),
                    transactionDTO.type(),
                    transactionDTO.originAccountNumber(),
                    transactionDTO.destinationAccountNumber(),
                    transactionDTO.idempotencyId(),
                    transactionDTO.creditCardNumber(),
                    transactionDTO.creditCardCvv())).toList();
        }

        return new Account(
                accountDTO.number(),
                accountDTO.agency(),
                accountDTO.balance(),
                accountDTO.password(),
                accountDTO.active(),
                accountDTO.cpf(),
                creditCardAggregate,
                transactions);
    }
}
