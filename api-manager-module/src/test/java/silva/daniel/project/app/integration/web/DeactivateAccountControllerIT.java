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
import silva.daniel.project.app.domain.account.entity.AccountKey;
import silva.daniel.project.app.domain.account.repository.AccountRepository;
import silva.daniel.project.app.domain.account.request.DeactivateAccountRequest;
import silva.daniel.project.app.domain.client.FailureResponse;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class DeactivateAccountControllerIT extends MysqlTestContainer implements IntegrationAssertCommons {

    private static final String API_DEACTIVATE_ACCOUNT = "/api/account/deactivate";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private RequestIntegrationCommons requestCommons;

    @Test
    void deactivateAccount_WithValidData_ReturnsSuccess() {
        var request = new DeactivateAccountRequest("12345678901", 1, 1234);
        var response = restTemplate.postForEntity(API_DEACTIVATE_ACCOUNT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        var sut = repository.findById(new AccountKey(request.getAccount(), request.getAgency()));
        assertThat(sut).isPresent();
        assertThat(sut.get().isActive()).isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void deactivateAccount_WithInvalidData_ReturnsStatus406(DeactivateAccountRequest request) {
        requestCommons.assertPostRequest(API_DEACTIVATE_ACCOUNT, request, FailureResponse.class, this::assertInvalidData);
    }

    @Test
    void deactivateAccount_WithClientNotExists_ReturnsStatus404() {
        var request = new DeactivateAccountRequest("12345678900", 1, 1235);
        requestCommons.assertPostRequest(API_DEACTIVATE_ACCOUNT, request, FailureResponse.class, this::assertClientNotExists);
    }

    @Test
    void deactivateAccount_WithClientDeactivated_ReturnsStatus409() {
        var request = new DeactivateAccountRequest("12345678903", 1, 1235);
        requestCommons.assertPostRequest(API_DEACTIVATE_ACCOUNT, request, FailureResponse.class, this::assertClientDeactivatedExists);
    }

    @Test
    void deactivateAccount_WithAccountNotExists_ReturnsStatus404() {
        var request = new DeactivateAccountRequest("12345678988", 2, 1235);
        requestCommons.assertPostRequest(API_DEACTIVATE_ACCOUNT, request, FailureResponse.class, this::assertAccountNotExists);
    }

    @Test
    void deactivateAccount_WithAccountAlreadyDeactivated_ReturnsStatus409() {
        var request = new DeactivateAccountRequest("12345678905", 5, 1234);
        requestCommons.assertPostRequest(API_DEACTIVATE_ACCOUNT, request, FailureResponse.class, this::assertAccountAlreadyDeactivated);
    }

    private static Stream<Arguments> provideInvalidData() {
        return Stream.of(
                Arguments.of(new DeactivateAccountRequest("1234567890", 1, 1235)),
                Arguments.of(new DeactivateAccountRequest("12345678900000000000", 1, 1235)),
                Arguments.of(new DeactivateAccountRequest("", 1, 1235)),
                Arguments.of(new DeactivateAccountRequest(null, 1, 1235)),
                Arguments.of(new DeactivateAccountRequest("12345678900", null, 1235)),
                Arguments.of(new DeactivateAccountRequest("12345678900", 1, null)),
                Arguments.of(new DeactivateAccountRequest("12345678900", -1, 1235)),
                Arguments.of(new DeactivateAccountRequest("12345678900", 0, 1235)),
                Arguments.of(new DeactivateAccountRequest("12345678900", 1, -1235)),
                Arguments.of(new DeactivateAccountRequest("12345678900", 1, 0))
        );
    }
}