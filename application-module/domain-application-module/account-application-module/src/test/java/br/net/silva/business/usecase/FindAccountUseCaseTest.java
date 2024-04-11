package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.FindAccountDTO;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAccountUseCaseTest {

    private FindAccountUseCase findAccountUseCase;

    @Mock
    private FindApplicationBaseGateway<AccountOutput> findGateway;

    @BeforeEach
    void setUp() {
        findAccountUseCase = new FindAccountUseCase(findGateway, new GenericResponseMapper(List.of()));
    }

    @Test
    void findAccount_WithValidData_ReturnsAccount() throws GenericException {
        var accountOutput = new AccountOutput(
                123,
                1,
                BigDecimal.valueOf(1000),
                "abc",
                true,
                "333444",
                null,
                Collections.emptyList());

        when(findGateway.findById(any())).thenReturn(Optional.of(accountOutput));

        var source = new Source(new GetInformationAccountOutput(), new FindAccountDTO("333444", 1, 123, "abc"));
        var sut = findAccountUseCase.exec(source);

        assertThat(sut).isNotNull();

        assertThat(sut.number()).isEqualTo(accountOutput.number());
        assertThat(sut.agency()).isEqualTo(accountOutput.agency());
        assertThat(sut.balance()).isEqualTo(accountOutput.balance());
        assertThat(sut.cpf()).isEqualTo(accountOutput.cpf());
        assertThat(sut.active()).isEqualTo(accountOutput.active());
        assertThat(sut.password()).isEqualTo(accountOutput.password());
        assertThat(sut.transactions()).isEqualTo(accountOutput.transactions());
        assertThat(sut.creditCard()).isEqualTo(accountOutput.creditCard());
    }

    @Test
    void findAccount_WithAccountNotExists_ReturnsException() throws GenericException {
        var accountOutput = new AccountOutput(
                123,
                1,
                BigDecimal.valueOf(1000),
                "abc",
                true,
                "333444",
                null,
                Collections.emptyList());

        when(findGateway.findById(any())).thenReturn(Optional.empty());

        var source = new Source(new GetInformationAccountOutput(), new FindAccountDTO("333444", 1, 123, "abc"));

        assertThatThrownBy(() -> findAccountUseCase.exec(source))
                .isInstanceOf(GenericException.class)
                .hasMessage("Account not found");
    }
}