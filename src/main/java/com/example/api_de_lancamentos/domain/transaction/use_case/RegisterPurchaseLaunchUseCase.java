package com.example.api_de_lancamentos.domain.transaction.use_case;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.shared.interfaces.*;
import com.example.api_de_lancamentos.domain.transaction.dto.TransactionRequestDTO;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;
import com.example.api_de_lancamentos.domain.transaction.factory.TransactionFactory;
import com.example.api_de_lancamentos.domain.transaction.factory.TransactionPolicyFactory;

import java.util.List;

public class RegisterPurchaseLaunchUseCase implements UseCase<List<Transaction>, TransactionRequestDTO> {

    private final CreateRepository<List<Transaction>> repository;
    private final FindRepository<Account, Long> accountFindRepository;
    private final UpdateRepository<Account> accountUpdateRepository;

    public RegisterPurchaseLaunchUseCase(CreateRepository<List<Transaction>> repository, FindRepository<Account, Long> accountFindRepository, UpdateRepository<Account> accountUpdateRepository) {
        this.repository = repository;
        this.accountFindRepository = accountFindRepository;
        this.accountUpdateRepository = accountUpdateRepository;
    }

    @Override
    public List<Transaction> execute(TransactionRequestDTO entity) {
        List<Transaction> transactions = TransactionFactory.createTransactions(entity.getTransactions());
        Account account = accountFindRepository.findBy(entity.getAccountNumber());
        account.addTransactions(transactions);

        List<TransactionPolicy<Account>> policies = TransactionPolicyFactory.getTransactionPolicy();
        for (TransactionPolicy<Account> policy : policies) {
            policy.addAccount(account);
            account = policy.executeTransaction();
        }

        accountUpdateRepository.update(account);
        return repository.create(transactions);
    }
}
