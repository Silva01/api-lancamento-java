package br.net.silva.business.usecase;

import br.net.silva.business.build.TransactionBuilder;
import br.net.silva.business.mapper.GetInformationMapper;
import br.net.silva.business.value_object.input.GetInformationAccountInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GetInformationAccountUseCaseTest {

    private GetInformationAccountUseCase useCase;

    @Mock
    private Repository<AccountOutput> findInformationRepository;

    @Mock
    private Repository<List<TransactionOutput>> transactionsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findInformationRepository.exec(anyString())).thenReturn(buildMockAccount(true));
        when(transactionsRepository.exec(anyString(), anyInt())).thenReturn(buildListTransaction());

        // Use Case
        useCase = new GetInformationAccountUseCase(findInformationRepository, transactionsRepository, new GenericResponseMapper(List.of(new GetInformationMapper())));
    }

    @Test
    void shouldGetInformationAccountWithSuccess() throws GenericException {
        var getInformationInput = new GetInformationAccountInput("99988877766");
        var source = new Source(new GetInformationAccountOutput(), getInformationInput);

        useCase.exec(source);

        var output = (GetInformationAccountOutput) source.output();
        assertNotNull(output);

        assertEquals(1, output.getAccountNumber());
        assertEquals(45678, output.getAgency());
        assertEquals(BigDecimal.valueOf(1000), output.getBalance());
        assertFalse(output.isHaveCreditCard());
        assertEquals(10, output.getTransactions().size());
        assertEquals(TransactionBuilder.buildFullTransactionListDto().createFrom(buildListTransaction()), output.getTransactions());
    }

    private AccountOutput buildMockAccount(boolean active) {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", Collections.emptyList(), null);
    }

    private List<TransactionOutput> buildListTransaction() {
        return List.of(
                new TransactionOutput(1L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new TransactionOutput(2L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new TransactionOutput(3L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new TransactionOutput(4L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new TransactionOutput(5L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new TransactionOutput(6L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new TransactionOutput(7L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new TransactionOutput(8L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new TransactionOutput(9L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new TransactionOutput(10L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321)
        );
    }

}