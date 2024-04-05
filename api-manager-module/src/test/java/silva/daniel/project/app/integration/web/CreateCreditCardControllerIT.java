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
import silva.daniel.project.app.domain.account.request.CreateCreditCardRequest;
import silva.daniel.project.app.domain.client.FailureResponse;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_DEACTIVATED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_DEACTIVATED;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CREDIT_CARD_ALREADY_EXISTS_MESSAGE;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class CreateCreditCardControllerIT extends MysqlTestContainer implements IntegrationAssertCommons {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private RequestIntegrationCommons requestCommons;

    @Test
    void createCreditCard_WithValidData_ReturnsStatus200() {
        var request = new CreateCreditCardRequest("12345678901", 1234, 1);
        var response = restTemplate.postForEntity("/credit-card", request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var account = repository.findByCpf(request.cpf());
        assertThat(account.isPresent()).isTrue();
        assertThat(account.get().getCreditCard()).isNotNull();
        assertThat(account.get().getCreditCard().isActive()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void createCreditCard_WithInvalidData_ReturnsStatus406(CreateCreditCardRequest request) {
        requestCommons.assertPostRequest("/credit-card", request, FailureResponse.class, this::assertInvalidData);
    }

    @Test
    void createCreditCard_WithClientNotExists_ReturnsStatus404() {
        var request = new CreateCreditCardRequest("12345678900", 1234, 1);
        var response = restTemplate.postForEntity("/credit-card", request, FailureResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo(CLIENT_NOT_FOUND_MESSAGE.getMessage());
        assertThat(response.getBody().getStatusCode()).isEqualTo(CLIENT_NOT_FOUND_MESSAGE.getStatusCode());
    }

    @Test
    void createCreditCard_WithClientDeactivated_ReturnsStatus409() {
        var request = new CreateCreditCardRequest("12345678903", 1234, 1);
        var response = restTemplate.postForEntity("/credit-card", request, FailureResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(CONFLICT);
        assertThat(response.getBody().getMessage()).isEqualTo(CLIENT_DEACTIVATED.getMessage());
        assertThat(response.getBody().getStatusCode()).isEqualTo(CLIENT_DEACTIVATED.getStatusCode());
    }

    @Test
    void createCreditCard_WithAccountNotExists_ReturnsStatus404() {
        var request = new CreateCreditCardRequest("12345678901", 1299, 1);
        var response = restTemplate.postForEntity("/credit-card", request, FailureResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo(ACCOUNT_NOT_FOUND_MESSAGE.getMessage());
        assertThat(response.getBody().getStatusCode()).isEqualTo(ACCOUNT_NOT_FOUND_MESSAGE.getStatusCode());
    }

    @Test
    void createCreditCard_WithAccountDeactivated_ReturnsStatus409() {
        var request = new CreateCreditCardRequest("12345678904", 1236, 1);
        var response = restTemplate.postForEntity("/credit-card", request, FailureResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(CONFLICT);
        assertThat(response.getBody().getMessage()).isEqualTo(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getMessage());
        assertThat(response.getBody().getStatusCode()).isEqualTo(ACCOUNT_ALREADY_DEACTIVATED_MESSAGE.getStatusCode());
    }

    @Test
    void createCreditCard_WithAlreadyHasCreditCard_ReturnsStatus409() {
        var request = new CreateCreditCardRequest("12345678910", 1237, 1);
        var response = restTemplate.postForEntity("/credit-card", request, FailureResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(CONFLICT);
        assertThat(response.getBody().getMessage()).isEqualTo(CREDIT_CARD_ALREADY_EXISTS_MESSAGE.getMessage());
        assertThat(response.getBody().getStatusCode()).isEqualTo(CREDIT_CARD_ALREADY_EXISTS_MESSAGE.getStatusCode());
    }

    private static Stream<Arguments> provideInvalidData() {
        return Stream.of(
                Arguments.of(new CreateCreditCardRequest("12345678901", 1234, null)),
                Arguments.of(new CreateCreditCardRequest("12345678901", null, 1)),
                Arguments.of(new CreateCreditCardRequest(null, 1234, 1)),
                Arguments.of(new CreateCreditCardRequest("", 1234, 1))
        );
    }
}