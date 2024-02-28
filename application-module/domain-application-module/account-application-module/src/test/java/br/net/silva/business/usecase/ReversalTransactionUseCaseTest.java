package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.build.TransactionBuilder;
import br.net.silva.business.interfaces.AbstractAccountBuilder;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ReversalTransactionUseCaseTest extends AbstractAccountBuilder {

    private ReversalTransactionUseCase useCase;

    @Mock
    private Repository<TransactionOutput> findTransactionByIdAndIdempotencyIdRepository;

    @Mock
    private Repository<AccountOutput> findAccountByAccountNumberRepository;

    @Mock
    private Repository<AccountOutput> saveAccountRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findTransactionByIdAndIdempotencyIdRepository.exec(anyLong(), anyLong())).thenReturn(TransactionBuilder.buildFullTransactionOutput().createFrom(buildMockTransaction(TransactionTypeEnum.DEBIT).build()));
        when(findAccountByAccountNumberRepository.exec(anyInt())).thenReturn(AccountBuilder.buildFullAccountOutput().createFrom(buildMockAccount(true, null).build()));
        when(saveAccountRepository.exec(any(Account.class))).thenReturn(AccountBuilder.buildFullAccountOutput().createFrom(buildMockAccount(true, null).build()));

        useCase = new ReversalTransactionUseCase(findTransactionByIdAndIdempotencyIdRepository, findAccountByAccountNumberRepository, saveAccountRepository);
    }

    @Test
    void shouldReverseTransactionWithSuccess() {
        var input = new ReversalTransactionInput(generateRandomIdTransaction(), generateIdempotencyId());
        var source = new Source(input);

        assertDoesNotThrow(() -> useCase.exec(source));

        verify(findTransactionByIdAndIdempotencyIdRepository, times(1)).exec(input.id(), input.idempotencyId());
        verify(findAccountByAccountNumberRepository, times(1)).exec(anyInt());
    }

}