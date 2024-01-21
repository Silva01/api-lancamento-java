package br.net.silva.business.usecase;

import br.net.silva.business.mapper.CreateResponseToFindAccountsByCpfFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountByClientFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountFactory;
import br.net.silva.business.value_object.input.FindAccountDTO;
import br.net.silva.business.value_object.output.AccountsByCpfResponseDto;
import br.net.silva.daniel.entity.Account;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class FindAllAccountsByCpfUseCaseTest {

    private FindAllAccountsByCpfUseCase useCase;

    private GenericResponseMapper factory;

    @Mock
    private Repository<List<Account>> repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        factory = new GenericResponseMapper(List.of(new CreateResponseToNewAccountByClientFactory(), new CreateResponseToNewAccountFactory(), new CreateResponseToFindAccountsByCpfFactory()));
        useCase = new FindAllAccountsByCpfUseCase(repository, factory);
    }

    @Test
    void shouldListAllAccountsByCpf() throws GenericException {
        when(repository.exec(anyString())).thenReturn(buildMockListAccount());
        var findAccountDto = new FindAccountDTO("99988877766", null, null, null);
        var source = new Source(new AccountsByCpfResponseDto(), findAccountDto);

        useCase.exec(source);

        var response = (AccountsByCpfResponseDto) source.output();
        assertNotNull(response);

        var mockListAccount = buildMockListAccount().stream().map(Account::build).toList();

        var accountsList = response.getAccounts();
        assertNotNull(accountsList);
        assertEquals(3, accountsList.size());
        assertEquals(mockListAccount, accountsList);
    }

    @Test
    void shouldListEmptyAccountsByCpf() throws GenericException {
        when(repository.exec(anyString())).thenReturn(Collections.emptyList());
        var findAccountDto = new FindAccountDTO("99988877766", null, null, null);
        var source = new Source(new AccountsByCpfResponseDto(), findAccountDto);

        useCase.exec(source);

        var response = (AccountsByCpfResponseDto) source.output();
        assertNotNull(response);

        var accountsList = response.getAccounts();
        assertNotNull(accountsList);
        assertTrue(accountsList.isEmpty());
    }

    private List<Account> buildMockListAccount() {
        var account1 = new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), true, "99988877766", null, Collections.emptyList());
        var account2 = new Account(2, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), false, "99988877766", null, Collections.emptyList());
        var account3 = new Account(3, 45680, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), false, "99988877766", null, Collections.emptyList());
        return List.of(account1, account2, account3);
    }

}