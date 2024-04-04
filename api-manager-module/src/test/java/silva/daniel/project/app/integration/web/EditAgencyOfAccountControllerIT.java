package silva.daniel.project.app.integration.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import silva.daniel.project.app.commons.MysqlTestContainer;
import silva.daniel.project.app.domain.account.entity.AccountKey;
import silva.daniel.project.app.domain.account.repository.AccountRepository;
import silva.daniel.project.app.domain.account.request.EditAgencyOfAccountRequest;
import silva.daniel.project.app.domain.client.FailureResponse;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class EditAgencyOfAccountControllerIT extends MysqlTestContainer {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository repository;

    @Test
    void editAgencyOfAccount_WithValidData_ReturnsStatus200() {
        final var request = new EditAgencyOfAccountRequest("12345678901", 1234, 1, 2);
        var httpEntity = new HttpEntity<>(request);
        var sut = restTemplate.exchange("/api/account/update/agency", HttpMethod.PUT, httpEntity, Void.class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

        var account = repository.findById(new AccountKey(request.accountNumber(), request.newAgencyNumber()));
        assertThat(account).isPresent();
        assertThat(account.get().getKeys().getBankAgencyNumber()).isEqualTo(request.newAgencyNumber());
    }


}
