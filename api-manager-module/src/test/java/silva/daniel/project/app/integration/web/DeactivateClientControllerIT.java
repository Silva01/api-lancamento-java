package silva.daniel.project.app.integration.web;

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
import silva.daniel.project.app.domain.client.FailureResponse;
import silva.daniel.project.app.domain.client.entity.repository.ClientRepository;
import silva.daniel.project.app.domain.client.request.DeactivateClient;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class DeactivateClientControllerIT extends MysqlTestContainer implements IntegrationAssertCommons {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientRepository repository;

    @Autowired
    private RequestIntegrationCommons requestCommons;

    @Test
    void deactivateClient_WithValidData_ReturnsStatus200() {
        final var request = new DeactivateClient("12345678901");
        var sut = restTemplate.postForEntity("/clients/deactivate", request, Void.class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

        var client = repository.findByCpf("12345678901");
        assertThat(client).isPresent();
        assertThat(client.get().isActive()).isFalse();
    }

    @Test
    void deactivateClient_WithClientActiveAndAccountAlreadyDeactivated_ReturnsStatus409() {
        final var request = new DeactivateClient("12345678904");
        requestCommons.assertPostRequest("/clients/deactivate", request, FailureResponse.class, this::assertAccountAlreadyDeactivated);
    }

    @Test
    void deactivateClient_WithInvalidData_ReturnsStatus406() {
        Stream.of(new DeactivateClient(""), new DeactivateClient(null))
                .forEach(request -> requestCommons.assertPostRequest("/clients/deactivate", request, FailureResponse.class, this::assertInvalidData));
    }

    @Test
    void deactivateClient_WithNoExistsClient_ReturnsStatus404() {
        final var request = new DeactivateClient("00000000000");
        requestCommons.assertPostRequest("/clients/deactivate", request, FailureResponse.class, this::assertClientNotExists);
    }

    @Test
    void deactivateClient_WithAlreadyDeactivatedClient_ReturnsStatus406() {
        final var request = new DeactivateClient("12345678903");
        requestCommons.assertPostRequest("/clients/deactivate", request, FailureResponse.class, this::assertClientAlreadyDeactivatedExists);
    }
}
