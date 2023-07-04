package com.example.api_de_lancamentos.domain.transaction.use_case;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.shared.interfaces.CreateRepository;
import com.example.api_de_lancamentos.domain.shared.interfaces.FindRepository;
import com.example.api_de_lancamentos.domain.shared.interfaces.PolicyStrategy;
import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;
import com.example.api_de_lancamentos.domain.transaction.dto.TransactionRequestDTO;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;
import com.example.api_de_lancamentos.domain.transaction.factory.TransactionFactory;
import com.example.api_de_lancamentos.domain.transaction.factory.TransactionPolicyFactory;
import com.example.api_de_lancamentos.domain.transaction.strategy.RegisterTransactionStrategy;

import java.math.BigDecimal;
import java.util.List;

public class RegisterPurchaseLaunchUseCase implements UseCase<List<Transaction>, TransactionRequestDTO> {

    private final CreateRepository<List<Transaction>> repository;
    private final FindRepository<Account, Long> accountFindRepository;
    private final UseCase<Boolean, Account> updateBalanceUseCase;
    private final PolicyStrategy<Account, BigDecimal> policyStrategy;

    public RegisterPurchaseLaunchUseCase(CreateRepository<List<Transaction>> repository, FindRepository<Account, Long> accountFindRepository, UseCase<Boolean, Account> updateBalanceUseCase) {
        this.repository = repository;
        this.accountFindRepository = accountFindRepository;
        this.updateBalanceUseCase = updateBalanceUseCase;
        this.policyStrategy = new RegisterTransactionStrategy(TransactionPolicyFactory.getTransactionPolicy());
    }

    @Override
    public List<Transaction> execute(TransactionRequestDTO entity) {
        List<Transaction> transactions = TransactionFactory.createTransactions(entity.getTransactions());
        Account account = accountFindRepository.findBy(entity.getAccountNumber());
        account.addTransactions(transactions);

        Account accountUpdated = policyStrategy.execute(account);

        updateBalanceUseCase.execute(accountUpdated);
        return repository.create(transactions);
    }
}
