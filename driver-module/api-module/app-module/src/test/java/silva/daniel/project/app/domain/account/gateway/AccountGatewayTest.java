package silva.daniel.project.app.domain.account.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import silva.daniel.project.app.domain.account.entity.Account;
import silva.daniel.project.app.domain.account.repository.AccountRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static silva.daniel.project.app.commons.AccountCommons.createAccountOutput;
import static silva.daniel.project.app.commons.AccountCommons.createEntity;

@SpringBootTest(classes = AccountGateway.class)
class AccountGatewayTest {

    @Autowired
    private AccountGateway accountGateway;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    void createAccount_WithValidData_ReturnsNewAccount() {
        final var entityMock = createEntity();
        when(accountRepository.save(any(Account.class))).thenReturn(entityMock);
        final var newAccount = createAccountOutput();
        final var sut = accountGateway.save(newAccount);

        assertThat(sut).isNotNull();
        assertThat(sut.transactions()).isEmpty();
        assertThat(sut.creditCard()).isNull();

        assertThat(sut.cpf()).isEqualTo(entityMock.getCpf());
        assertThat(sut.active()).isEqualTo(entityMock.isActive());
        assertThat(sut.balance()).isEqualTo(entityMock.getBalance());
        assertThat(sut.agency()).isEqualTo(entityMock.getKeys().getBankAgencyNumber());
        assertThat(sut.number()).isEqualTo(entityMock.getKeys().getNumber());
    }
}