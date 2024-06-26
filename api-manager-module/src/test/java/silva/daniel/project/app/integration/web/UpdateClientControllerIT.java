package silva.daniel.project.app.integration.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import silva.daniel.project.app.commons.IntegrationAssertCommons;
import silva.daniel.project.app.commons.MysqlTestContainer;
import silva.daniel.project.app.commons.RequestIntegrationCommons;
import silva.daniel.project.app.domain.client.FailureResponse;
import silva.daniel.project.app.domain.client.entity.repository.ClientRepository;
import silva.daniel.project.app.domain.client.request.EditClientRequest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CONFLICT;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_ALREADY_DEACTIVATED;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.INVALID_DATA_MESSAGE;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class UpdateClientControllerIT extends MysqlTestContainer implements IntegrationAssertCommons {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientRepository repository;

    @Autowired
    private RequestIntegrationCommons requestCommons;

    @Test
    void editClient_WithValidData_Returns201AndAccountData() {
        final var request = new EditClientRequest("12345678901", "Ace", "000000000");

        var httpEntity = new HttpEntity<EditClientRequest>(request);
        var sut = restTemplate.exchange("/clients", HttpMethod.PUT, httpEntity, Void.class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

        var client = repository.findByCpf("12345678901");
        assertThat(client).isPresent();
        assertThat(client.get().getName()).isEqualTo(request.name());
        assertThat(client.get().getCpf()).isEqualTo(request.cpf());
        assertThat(client.get().getTelephone()).isEqualTo(request.telephone());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void editClient_WithInvalidData_ReturnsStatus406(EditClientRequest request) {
        requestCommons.assertPutRequest("/clients", request, FailureResponse.class, this::assertInvalidData);
    }

    @Test
    void editClient_WithCpfNotExists_ReturnsStatus404() {
        final var request = new EditClientRequest("12345600000", "Ace", "000000000");
        requestCommons.assertPutRequest("/clients", request, FailureResponse.class, this::assertClientNotExists);
    }

    @Test
    void editClient_WithCpfDeactivated_ReturnsStatus409() {
        final var request = new EditClientRequest("12345678903", "Ace", "000000000");
        requestCommons.assertPutRequest("/clients", request, FailureResponse.class, this::assertClientAlreadyDeactivatedExists);
    }

    private static Stream<Arguments> provideInvalidData() {
        return Stream.of(
                Arguments.of(new EditClientRequest(
                        "",
                        "Daniel Silva",
                        "12345678901"
                )),
                Arguments.of(new EditClientRequest(
                        null,
                        "Daniel Silva",
                        "12345678901"
                )),
                Arguments.of(new EditClientRequest(
                        "12345678901",
                        "",
                        "12345678901"
                )),Arguments.of(new EditClientRequest(
                        "12345678901",
                        null,
                        "12345678901"
                ))

        );
    }

}