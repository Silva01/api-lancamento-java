package silva.daniel.project.app.integration.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import silva.daniel.project.app.commons.MysqlTestContainer;
import silva.daniel.project.app.domain.account.repository.AccountRepository;
import silva.daniel.project.app.domain.account.request.DeactivateCreditCardRequest;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class DeactivateCreditCardControllerIT extends MysqlTestContainer {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository repository;

    @Test
    void deactivateCreditCard_WithValidData_ReturnsStatus200() {
        var request = new DeactivateCreditCardRequest("12345678910", 1237, 1, "1234567890123456");
        var response = restTemplate.postForEntity("/credit-card/deactivate", request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        var account = repository.findByCpf(request.cpf());
        assertThat(account.isPresent()).isTrue();
        assertThat(account.get().getCreditCard().isActive()).isFalse();
    }
}