package silva.daniel.project.app.integration.web;

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
import silva.daniel.project.app.domain.account.repository.AccountRepository;
import silva.daniel.project.app.domain.account.request.DeactivateCreditCardRequest;
import silva.daniel.project.app.domain.client.FailureResponse;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class DeactivateCreditCardControllerIT extends MysqlTestContainer implements IntegrationAssertCommons {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private RequestIntegrationCommons requestCommons;

    @Test
    void deactivateCreditCard_WithValidData_ReturnsStatus200() {
        var request = new DeactivateCreditCardRequest("12345678910", 1237, 1, "1234567890123456");
        var response = restTemplate.postForEntity("/credit-card/deactivate", request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        var account = repository.findByCpf(request.cpf());
        assertThat(account.isPresent()).isTrue();
        assertThat(account.get().getCreditCard().isActive()).isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void deactivateCreditCard_WithInvalidData_ReturnsStatus406(DeactivateCreditCardRequest request) {
        requestCommons.assertPostRequest("/credit-card/deactivate", request, FailureResponse.class, this::assertInvalidData);
    }

    @Test
    void deactivateCreditCard_WithClientNotExists_ReturnsStatus404() {
        var request = new DeactivateCreditCardRequest("12345678999", 1237, 1, "1234567890123456");
        requestCommons.assertPostRequest("/credit-card/deactivate", request, FailureResponse.class, this::assertClientNotExists);
    }

    @Test
    void deactivateCreditCard_WithAccountNotExists_ReturnsStatus404() {
        var request = new DeactivateCreditCardRequest("12345678910", 1220, 33, "1234567890123456");
        requestCommons.assertPostRequest("/credit-card/deactivate", request, FailureResponse.class, this::assertAccountNotExists);
    }

    @Test
    void deactivateCreditCard_WithCreditCardNotExists_ReturnsStatus404() {
        var request = new DeactivateCreditCardRequest("12345678904", 1236, 1, "1234567890123456");
        requestCommons.assertPostRequest("/credit-card/deactivate", request, FailureResponse.class, this::assertCreditCardNotExists);
    }

    @Test
    void deactivateCreditCard_WithCreditCardAlreadyDeactivated_ReturnsStatus409() {
        var request = new DeactivateCreditCardRequest("12345678911", 1238, 1, "1234567890123457");
        requestCommons.assertPostRequest("/credit-card/deactivate", request, FailureResponse.class, this::assertCredicCardAlreadyDeactivated);
    }

    private static Stream<Arguments> provideInvalidData() {
        return Stream.of(
            Arguments.of(new DeactivateCreditCardRequest("", 1237, 1, "1234567890123456")),
            Arguments.of(new DeactivateCreditCardRequest("12345678910", 1237, 1, "")),
            Arguments.of(new DeactivateCreditCardRequest("12345678910", null, 1, "12345678901234567")),
            Arguments.of(new DeactivateCreditCardRequest("12345678910", 1237, null, "12345678901234567")),
            Arguments.of(new DeactivateCreditCardRequest("123456", 1237, 1, "1234567890123456")),
            Arguments.of(new DeactivateCreditCardRequest("12345678910", 1237, 1, "123456"))
        );
    }
}