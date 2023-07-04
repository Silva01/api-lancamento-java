package com.example.api_de_lancamentos.domain.transaction.use_case;

import com.example.api_de_lancamentos.domain.account.dto.CreatedAccountNumberDTO;
import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.account.exception.AccountNotExistsException;
import com.example.api_de_lancamentos.domain.account.factory.AccountFactory;
import com.example.api_de_lancamentos.domain.account.use_case.CreateAccountUseCase;
import com.example.api_de_lancamentos.domain.account.use_case.UpdateBalanceUseCase;
import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;
import com.example.api_de_lancamentos.domain.transaction.dto.TransactionDTO;
import com.example.api_de_lancamentos.domain.transaction.dto.TransactionRequestDTO;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;
import com.example.api_de_lancamentos.domain.transaction.enuns.TypeTransactionEnum;
import com.example.api_de_lancamentos.domain.transaction.exception.TransactionNotValidException;
import com.example.api_de_lancamentos.infrastructure.ApiDeLancamentosApplication;
import com.example.api_de_lancamentos.infrastructure.model.TransactionModel;
import com.example.api_de_lancamentos.infrastructure.repository.TransactionModelRepository;
import com.example.api_de_lancamentos.infrastructure.repository.impl.AccountModelRepositoryIMPL;
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
    private final AccountModelRepositoryIMPL accountModelRepositoryIMPL;
    private final TransactionModelRepository transactionModelRepository;

    @Autowired
    RegisterPurchaseLaunchUseCaseTest(TransactionModelRepositoryIMPL transactionRepository, AccountModelRepositoryIMPL accountModelRepositoryIMPL, TransactionModelRepository transactionModelRepository) {
        this.transactionRepository = transactionRepository;
        this.accountModelRepositoryIMPL = accountModelRepositoryIMPL;
        this.transactionModelRepository = transactionModelRepository;
    }


    @Test
    public void deve_registrar_lancamento_de_compra() {

        UseCase<CreatedAccountNumberDTO, Account> createAccountUseCase = new CreateAccountUseCase(accountModelRepositoryIMPL);
        CreatedAccountNumberDTO accountCreated = createAccountUseCase.execute(AccountFactory.createAccount("Teste"));

        assertNotNull(accountCreated.getAccountNumber());

        UseCase<List<Transaction>, TransactionRequestDTO> registerPurchaseLaunchUseCase = new RegisterPurchaseLaunchUseCase(transactionRepository, accountModelRepositoryIMPL, new UpdateBalanceUseCase(accountModelRepositoryIMPL));
        List<TransactionDTO> transactions = List.of(
                new TransactionDTO(0L, "Compra de um produto 1", TypeTransactionEnum.CREDIT, LocalDateTime.now(), new BigDecimal("100.00")),
                new TransactionDTO(0L, "Compra de um produto 2", TypeTransactionEnum.DEBIT, LocalDateTime.now(), new BigDecimal("1000.00"))
        );

        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(accountCreated.getAccountNumber(), transactions);
        registerPurchaseLaunchUseCase.execute(transactionRequestDTO);

        List<TransactionModel> transactionsSaved = transactionModelRepository.findAll();

        assertEquals(2, transactionsSaved.size());
    }

    @Test
    public void deve_dar_erro_ao_consultar_conta_inexistente() {

        UseCase<List<Transaction>, TransactionRequestDTO> registerPurchaseLaunchUseCase = new RegisterPurchaseLaunchUseCase(transactionRepository, accountModelRepositoryIMPL, new UpdateBalanceUseCase(accountModelRepositoryIMPL));
        List<TransactionDTO> transactions = List.of(
                new TransactionDTO(0L, "Compra de um produto 1", TypeTransactionEnum.CREDIT, LocalDateTime.now(), new BigDecimal("100.00")),
                new TransactionDTO(0L, "Compra de um produto 2", TypeTransactionEnum.DEBIT, LocalDateTime.now(), new BigDecimal("1000.00"))
        );

        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(100000L, transactions);
        AccountNotExistsException exception = assertThrows(AccountNotExistsException.class, () -> registerPurchaseLaunchUseCase.execute(transactionRequestDTO));

        assertEquals("Conta não encontrada", exception.getMessage());
    }

    @Test
    public void deve_dar_erro_ao_tentar_registrar_trasacoes_sem_saldo_no_credito() {

        UseCase<CreatedAccountNumberDTO, Account> createAccountUseCase = new CreateAccountUseCase(accountModelRepositoryIMPL);
        CreatedAccountNumberDTO accountCreated = createAccountUseCase.execute(AccountFactory.createAccount("Teste"));

        assertNotNull(accountCreated.getAccountNumber());

        UseCase<List<Transaction>, TransactionRequestDTO> registerPurchaseLaunchUseCase = new RegisterPurchaseLaunchUseCase(transactionRepository, accountModelRepositoryIMPL, new UpdateBalanceUseCase(accountModelRepositoryIMPL));
        List<TransactionDTO> transactions = List.of(
                new TransactionDTO(0L, "Compra de um produto 1", TypeTransactionEnum.CREDIT, LocalDateTime.now(), new BigDecimal("9000.00")),
                new TransactionDTO(0L, "Compra de um produto 2", TypeTransactionEnum.DEBIT, LocalDateTime.now(), new BigDecimal("1000.00"))
        );

        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(accountCreated.getAccountNumber(), transactions);
        TransactionNotValidException exception = assertThrows(TransactionNotValidException.class, () -> registerPurchaseLaunchUseCase.execute(transactionRequestDTO));

        assertEquals("Saldo insuficiente", exception.getMessage());
    }

    @Test
    public void deve_dar_erro_ao_tentar_registrar_trasacoes_sem_saldo_no_debito() {

        UseCase<CreatedAccountNumberDTO, Account> createAccountUseCase = new CreateAccountUseCase(accountModelRepositoryIMPL);
        CreatedAccountNumberDTO accountCreated = createAccountUseCase.execute(AccountFactory.createAccount("Teste"));

        assertNotNull(accountCreated.getAccountNumber());

        UseCase<List<Transaction>, TransactionRequestDTO> registerPurchaseLaunchUseCase = new RegisterPurchaseLaunchUseCase(transactionRepository, accountModelRepositoryIMPL, new UpdateBalanceUseCase(accountModelRepositoryIMPL));
        List<TransactionDTO> transactions = List.of(
                new TransactionDTO(0L, "Compra de um produto 1", TypeTransactionEnum.CREDIT, LocalDateTime.now(), new BigDecimal("100.00")),
                new TransactionDTO(0L, "Compra de um produto 2", TypeTransactionEnum.DEBIT, LocalDateTime.now(), new BigDecimal("9000.00"))
        );

        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(accountCreated.getAccountNumber(), transactions);
        TransactionNotValidException exception = assertThrows(TransactionNotValidException.class, () -> registerPurchaseLaunchUseCase.execute(transactionRequestDTO));

        assertEquals("Saldo insuficiente", exception.getMessage());
    }
}