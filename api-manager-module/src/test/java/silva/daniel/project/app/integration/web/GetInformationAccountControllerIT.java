package silva.daniel.project.app.integration.web;

import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import br.net.silva.daniel.value_object.output.GetInformationClientResponse;
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
import silva.daniel.project.app.domain.client.entity.repository.ClientRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class GetInformationAccountControllerIT extends MysqlTestContainer implements IntegrationAssertCommons {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private RequestIntegrationCommons requestCommons;

    @Test
    void getClient_WithValidData_ReturnsClient() {
        final var cpf = "12345678901";
        final var sut = restTemplate.getForEntity("/api/account/{cpf}", GetInformationAccountOutput.class, cpf);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

        var account = repository.findByCpf(cpf).orElse(null);
        assertThat(account).isNotNull();

        assertThat(sut.getBody().getAccountNumber()).isEqualTo(account.getKeys().getNumber());
        assertThat(sut.getBody().getAgency()).isEqualTo(account.getKeys().getBankAgencyNumber());
        assertThat(sut.getBody().getBalance()).isEqualTo(account.getBalance());
    }

    @Test
    void getClient_WithClientNotExists_Returns404() {
        requestCommons.assertGetRequest("/clients/{cpf}", FailureResponse.class, this::assertClientNotExists, "12345678933");
    }

}