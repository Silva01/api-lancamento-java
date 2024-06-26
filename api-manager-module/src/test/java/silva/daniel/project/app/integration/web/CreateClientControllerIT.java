package silva.daniel.project.app.integration.web;

import br.net.silva.business.value_object.output.NewAccountByNewClientResponseSuccess;
import br.net.silva.daniel.value_object.input.AddressRequestDTO;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
import silva.daniel.project.app.domain.client.request.ClientRequest;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CONFLICT;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class CreateClientControllerIT extends MysqlTestContainer implements IntegrationAssertCommons {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RequestIntegrationCommons requestCommons;

    @Test
    void createClient_WithValidData_Returns201AndAccountData() {
        var request = new ClientRequestDTO(
                UUID.randomUUID().toString(),
                "12345678902",
                "Daniel Silva",
                "12345678901",
                true,
                1234,
                new AddressRequestDTO("Rua 1", "123", "Casa", "Bairro", "SP", "Cidade", "12345678")
        );

        var sut = restTemplate.postForEntity("/clients", request, NewAccountByNewClientResponseSuccess.class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(sut.getBody()).isNotNull();
        assertThat(sut.getBody().getAccountNumber()).isNotNull();
        assertThat(sut.getBody().getAgency()).isEqualTo(1234);
        assertThat(sut.getBody().getProvisionalPassword()).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void createClient_WithInvalidData_ReturnsStatus406(ClientRequest request) {
        requestCommons.assertPostRequest("/clients", request, FailureResponse.class, this::assertInvalidData);
    }

    @Test
    void createClient_WithExistsClient_ReturnsStatus409() {
        var request = new ClientRequest(
                null,
                "12345678901",
                "Daniel Silva",
                "12345678901",
                true,
                1234,
                new AddressRequestDTO("Rua 1", "123", "Casa", "Bairro", "SP", "Cidade", "12345678")
        );

        requestCommons.assertPostRequest("/clients", request, FailureResponse.class, this::assertClientAlreadyExists);
    }

    @Test
    void createClient_WithClientDeactivated_ReturnsStatus409() {
        var request = new ClientRequestDTO(
                UUID.randomUUID().toString(),
                "12345678903",
                "Daniel Silva",
                "12345678901",
                true,
                1234,
                new AddressRequestDTO("Rua 1", "123", "Casa", "Bairro", "SP", "Cidade", "12345678")
        );

        requestCommons.assertPostRequest("/clients", request, FailureResponse.class, this::assertClientAlreadyExists);
    }

    private static Stream<Arguments> provideInvalidData() {
        return Stream.of(
                Arguments.of(new ClientRequest(
                        null,
                        "",
                        "Daniel Silva",
                        "12345678901",
                        true,
                        1234,
                        new AddressRequestDTO("Rua 1", "123", "Casa", "Bairro", "SP", "Cidade", "12345678")
                )),
                Arguments.of(new ClientRequest(
                        null,
                        null,
                        "Daniel Silva",
                        "12345678901",
                        true,
                        1234,
                        new AddressRequestDTO("Rua 1", "123", "Casa", "Bairro", "SP", "Cidade", "12345678")
                )),
                Arguments.of(new ClientRequest(
                        null,
                        "12345678901",
                        "Daniel Silva",
                        "12345678901",
                        true,
                        0,
                        new AddressRequestDTO("Rua 1", "123", "Casa", "Bairro", "SP", "Cidade", "12345678")
                )),
                Arguments.of(new ClientRequest(
                        null,
                        "12345678901",
                        "Daniel Silva",
                        "12345678901",
                        true,
                        -1,
                        new AddressRequestDTO("Rua 1", "123", "Casa", "Bairro", "SP", "Cidade", "12345678")
                )),
                Arguments.of(new ClientRequest(
                        null,
                        "12345678901",
                        "Daniel Silva",
                        "12345678901",
                        true,
                        null,
                        new AddressRequestDTO("Rua 1", "123", "Casa", "Bairro", "SP", "Cidade", "12345678")
                )),
                Arguments.of(new ClientRequest(
                        null,
                        "12345678901",
                        "Daniel Silva",
                        "12345678901",
                        true,
                        1234,
                        null
                ))
        );
    }

}