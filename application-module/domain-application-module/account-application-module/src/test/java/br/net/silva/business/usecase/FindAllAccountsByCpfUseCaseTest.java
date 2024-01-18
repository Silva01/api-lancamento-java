package br.net.silva.business.usecase;

import br.net.silva.business.value_object.output.AccountResponseDto;
import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class FindAllAccountsByCpfUseCaseTest {

    private FindAllAccountsByCpfUseCase useCase;

    @Mock
    private Repository<List<Account>> repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new FindAllAccountsByCpfUseCase(repository);
    }

    @Test
    void shouldListAllAccountsByCpf() throws GenericException {
        when(repository.exec(anyString())).thenReturn(buildMockListAccount());
        Map<String, String> inputMap = Map.of("cpf", "99988877766");
        var source = new Source(new HashMap<>(), inputMap);

        useCase.exec(source);

        var response = (AccountResponseDto) source.map().get(TypeAccountMapperEnum.ACCOUNT.name());
        assertNotNull(response);

        var mockListAccount = buildMockListAccount().stream().map(Account::build).toList();

        var accountsList = response.accounts();
        assertNotNull(accountsList);
        assertEquals(3, accountsList.size());
        assertEquals(mockListAccount, accountsList);
    }

    @Test
    void shouldListEmptyAccountsByCpf() throws GenericException {
        when(repository.exec(anyString())).thenReturn(Collections.emptyList());
        Map<String, String> inputMap = Map.of("cpf", "99988877766");
        var source = new Source(new HashMap<>(), inputMap);

        useCase.exec(source);

        var response = (AccountResponseDto) source.map().get(TypeAccountMapperEnum.ACCOUNT.name());
        assertNotNull(response);

        var accountsList = response.accounts();
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