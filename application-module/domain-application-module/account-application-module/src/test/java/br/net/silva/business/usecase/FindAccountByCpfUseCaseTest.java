package br.net.silva.business.usecase;

import br.net.silva.business.mapper.CreateResponseToNewAccountByClientFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountFactory;
import br.net.silva.business.value_object.input.FindAccountDTO;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAccountByCpfUseCaseTest {

    private FindAccountByCpfUseCase findAccountByCpfUseCase;

    private GenericResponseMapper factory;

    @Mock
    private FindApplicationBaseGateway<AccountOutput> findActiveAccountRepository;

    @BeforeEach
    void setUp() {
        this.factory = new GenericResponseMapper(List.of(new CreateResponseToNewAccountFactory(), new CreateResponseToNewAccountByClientFactory()));
        this.findAccountByCpfUseCase = new FindAccountByCpfUseCase(findActiveAccountRepository, factory);
    }

    @Test
    void findAccountByCpf_WithValidParams_ReturnAccount() throws GenericException {
        when(findActiveAccountRepository.findById(Mockito.any())).thenReturn(Optional.of(createAccountOutputMock(true)));
        final var findAccountRequest = new FindAccountDTO("88899900055", 1, 2, "test");

        final var sut = findAccountByCpfUseCase.exec(Source.of(findAccountRequest));
        assertThat(sut).isNotNull();
        assertThat(sut.cpf()).isEqualTo(createAccountOutputMock(true).cpf());
        assertThat(sut.agency()).isEqualTo(createAccountOutputMock(true).agency());
        assertThat(sut.number()).isEqualTo(createAccountOutputMock(true).number());
        assertThat(sut.balance()).isEqualTo(createAccountOutputMock(true).balance());
        assertThat(sut.active()).isEqualTo(createAccountOutputMock(true).active());
        assertThat(sut.password()).isEqualTo(createAccountOutputMock(true).password());

        verify(findActiveAccountRepository, times(1)).findById(Mockito.any());
    }

    private static AccountOutput createAccountOutputMock(boolean status) {
        return new AccountOutput(
                1,
                2,
                BigDecimal.valueOf(2000),
                "test",
                status,
                "00099988877",
                null,
                new ArrayList<>()
        );
    }
}