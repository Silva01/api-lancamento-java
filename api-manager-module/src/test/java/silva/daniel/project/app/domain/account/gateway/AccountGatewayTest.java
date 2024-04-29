package silva.daniel.project.app.domain.account.gateway;

import br.net.silva.business.value_object.input.ChangePasswordDTO;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import silva.daniel.project.app.domain.account.entity.Account;
import silva.daniel.project.app.domain.account.entity.AccountKey;
import silva.daniel.project.app.domain.account.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static silva.daniel.project.app.commons.AccountCommons.createAccountOutput;
import static silva.daniel.project.app.commons.AccountCommons.createEntity;

@ActiveProfiles("unit")
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

    @Test
    void findAccount_WithAccountNumberAndAgency_ReturnsAccountData() {
        final var entityMock = createEntity();
        when(accountRepository.findById(any(AccountKey.class))).thenReturn(Optional.of(entityMock));

        final var accountOpt = accountGateway.findById(new AccountParamMock());
        assertThat(accountOpt).isPresent();

        assertThat(accountOpt.get().number()).isEqualTo(entityMock.getKeys().getNumber());
        assertThat(accountOpt.get().agency()).isEqualTo(entityMock.getKeys().getBankAgencyNumber());
        assertThat(accountOpt.get().balance()).isEqualTo(entityMock.getBalance());
        assertThat(accountOpt.get().password()).isEqualTo(entityMock.getPassword());
        assertThat(accountOpt.get().active()).isEqualTo(entityMock.isActive());
        assertThat(accountOpt.get().cpf()).isEqualTo(entityMock.getCpf());

    }

    private class AccountParamMock implements IAccountParam {

        private final BigDecimal balance;
        private final String password;
        private final boolean active;
        private final Integer accountNumber;
        private final Integer agency;
        private final String cpf;

        public AccountParamMock(BigDecimal balance, String password, boolean active, Integer accountNumber, Integer agency, String cpf) {
            this.balance = balance;
            this.password = password;
            this.active = active;
            this.accountNumber = accountNumber;
            this.agency = agency;
            this.cpf = cpf;
        }

        public AccountParamMock() {
            this(BigDecimal.valueOf(1000), "test", true, 1234, 1, "99988877766");
        }


        @Override
        public BigDecimal balance() {
            return balance;
        }

        @Override
        public String password() {
            return password;
        }

        @Override
        public boolean active() {
            return active;
        }

        @Override
        public Integer accountNumber() {
            return accountNumber;
        }

        @Override
        public Integer agency() {
            return agency;
        }

        @Override
        public String cpf() {
            return cpf;
        }
    }
}