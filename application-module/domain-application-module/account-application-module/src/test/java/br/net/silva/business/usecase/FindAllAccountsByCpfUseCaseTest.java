package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.mapper.CreateResponseToFindAccountsByCpfFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountByClientFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountFactory;
import br.net.silva.business.value_object.input.FindAccountDTO;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.AccountsByCpfResponseDto;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllAccountsByCpfUseCaseTest {

    private FindAllAccountsByCpfUseCase useCase;

    private GenericResponseMapper factory;

    @Mock
    private FindApplicationBaseGateway<AccountOutput> findGateway;

    @BeforeEach
    void setUp() {
        factory = new GenericResponseMapper(List.of(new CreateResponseToNewAccountByClientFactory(), new CreateResponseToNewAccountFactory(), new CreateResponseToFindAccountsByCpfFactory()));
        useCase = new FindAllAccountsByCpfUseCase(findGateway, factory);
    }

    @Test
    void shouldListAllAccountsByCpf() throws GenericException {
        when(findGateway.findAllBy(any(ParamGateway.class))).thenReturn(buildMockListAccount());
        var findAccountDto = new FindAccountDTO("99988877766", null, null, null);
        var source = new Source(new AccountsByCpfResponseDto(), findAccountDto);

        useCase.exec(source);

        var response = (AccountsByCpfResponseDto) source.output();
        assertNotNull(response);

        var mockListAccount = buildMockListAccount().stream().map(AccountBuilder.buildFullAccountDto()::createFrom).toList();

        var accountsList = response.getAccounts();
        assertNotNull(accountsList);
        assertEquals(3, accountsList.size());
        assertEquals(mockListAccount, accountsList);
    }

    @Test
    void shouldListEmptyAccountsByCpf() throws GenericException {
        when(findGateway.findAllBy(any(ParamGateway.class))).thenReturn(Collections.emptyList());
        var findAccountDto = new FindAccountDTO("99988877766", null, null, null);
        var source = new Source(new AccountsByCpfResponseDto(), findAccountDto);

        useCase.exec(source);

        var response = (AccountsByCpfResponseDto) source.output();
        assertNotNull(response);

        var accountsList = response.getAccounts();
        assertNotNull(accountsList);
        assertTrue(accountsList.isEmpty());
    }

    private List<AccountOutput> buildMockListAccount() {
        var account1 = new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), true, "99988877766", null, Collections.emptyList());
        var account2 = new AccountOutput(2, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), false, "99988877766", null, Collections.emptyList());
        var account3 = new AccountOutput(3, 45680, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), false, "99988877766", null, Collections.emptyList());
        return List.of(account1, account2, account3);
    }

}