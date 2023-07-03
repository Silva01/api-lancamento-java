package com.example.api_de_lancamentos.domain.transaction.use_case;

import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;
import com.example.api_de_lancamentos.domain.transaction.enuns.TypeTransactionEnum;
import com.example.api_de_lancamentos.infrastructure.ApiDeLancamentosApplication;
import com.example.api_de_lancamentos.infrastructure.model.TransactionModel;
import com.example.api_de_lancamentos.infrastructure.repository.TransactionModelRepository;
import com.example.api_de_lancamentos.infrastructure.repository.impl.TransactionModelRepositoryIMPL;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(classes = {ApiDeLancamentosApplication.class})
class RegisterPurchaseLaunchUseCaseTest {

    private final TransactionModelRepositoryIMPL transactionRepository;
    private final TransactionModelRepository transactionModelRepository;

    @Autowired
    RegisterPurchaseLaunchUseCaseTest(TransactionModelRepositoryIMPL transactionRepository, TransactionModelRepository transactionModelRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionModelRepository = transactionModelRepository;
    }


    @Test
    public void deve_registrar_lancamento_de_compra() {
        UseCase<List<Transaction>, List<Transaction>> registerPurchaseLaunchUseCase = new RegisterPurchaseLaunchUseCase(transactionRepository);
        List<Transaction> transactions = List.of(
                new Transaction(0L, "Compra de um produto 1", TypeTransactionEnum.CREDIT, LocalDateTime.now(), new BigDecimal("100.00")),
                new Transaction(0L, "Compra de um produto 2", TypeTransactionEnum.DEBIT, LocalDateTime.now(), new BigDecimal("1000.00"))
        );

        registerPurchaseLaunchUseCase.execute(transactions);

        List<TransactionModel> transactionsSaved = transactionModelRepository.findAll();

        assertEquals(2, transactionsSaved.size());
    }
}