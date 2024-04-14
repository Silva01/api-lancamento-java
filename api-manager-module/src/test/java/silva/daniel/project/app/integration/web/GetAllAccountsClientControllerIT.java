package silva.daniel.project.app.integration.web;

import br.net.silva.business.value_object.output.AccountsByCpfResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import silva.daniel.project.app.commons.IntegrationAssertCommons;
import silva.daniel.project.app.commons.MysqlTestContainer;
import silva.daniel.project.app.commons.RequestIntegrationCommons;
import silva.daniel.project.app.domain.account.repository.AccountRepository;
import silva.daniel.project.app.domain.client.FailureResponse;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class GetAllAccountsClientControllerIT extends MysqlTestContainer implements IntegrationAssertCommons {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private RequestIntegrationCommons requestCommons;

    @Test
    void getAllAccounts_WithValidData_ReturnsAccounts() {
        final var cpf = "12345678901";
        final var sut = restTemplate.getForEntity("/api/account/all/{cpf}", AccountsByCpfResponseDto.class, cpf);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isNotNull();
        assertThat(sut.getBody().getAccounts()).hasSize(1);

        var client = repository.findByCpf(cpf).orElse(null);
        assertThat(client).isNotNull();
        assertThat(sut.getBody().getAccounts().get(0).agency()).isEqualTo(client.getKeys().getBankAgencyNumber());
        assertThat(sut.getBody().getAccounts().get(0).number()).isEqualTo(client.getKeys().getNumber());
        assertThat(sut.getBody().getAccounts().get(0).cpf()).isEqualTo(client.getCpf());
        assertThat(sut.getBody().getAccounts().get(0).balance()).isEqualTo(client.getBalance());
    }

    @Test
    void getAllAccounts_WithClientNotExists_ReturnsEmptyList() {
        final var cpf = "12345679999";
        final var sut = restTemplate.getForEntity("/api/account/all/{cpf}", AccountsByCpfResponseDto.class, cpf);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isNotNull();
        assertThat(sut.getBody().getAccounts()).isEmpty();
    }

    @Test
    void getAllAccounts_WithAccountNotExistsForCpf_ReturnsEmptyList() {
        final var cpf = "12345678988";
        final var sut = restTemplate.getForEntity("/api/account/all/{cpf}", AccountsByCpfResponseDto.class, cpf);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(sut.getBody()).isNotNull();
        assertThat(sut.getBody().getAccounts()).isEmpty();
    }
}