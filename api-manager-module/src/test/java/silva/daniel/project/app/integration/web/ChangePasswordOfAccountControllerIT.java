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
import silva.daniel.project.app.domain.account.entity.AccountKey;
import silva.daniel.project.app.domain.account.repository.AccountRepository;
import silva.daniel.project.app.domain.account.request.ActivateAccountRequest;
import silva.daniel.project.app.domain.account.request.ChangePasswordRequest;
import silva.daniel.project.app.domain.client.FailureResponse;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class ChangePasswordOfAccountControllerIT extends MysqlTestContainer implements IntegrationAssertCommons {

    private static final String API_CHANGE_PASSWORD = "/api/account/change/password";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private RequestIntegrationCommons requestCommons;

    @Test
    void changePassword_WithValidData_ReturnsSuccess() {
        var request = new ChangePasswordRequest(1, 1234, "12345678901", "123456", "654321");
        var initialData = repository.findById(new AccountKey(request.getAccountNumber(), request.getAgency()));
        assertThat(initialData).isPresent();

        var httpEntity = new HttpEntity<ChangePasswordRequest>(request);
        var response = restTemplate.exchange(API_CHANGE_PASSWORD, HttpMethod.PUT, httpEntity, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        var sut = repository.findById(new AccountKey(request.getAccountNumber(), request.getAgency()));
        assertThat(sut).isPresent();
        assertThat(sut.get().getPassword()).isNotEqualTo(initialData.get().getPassword());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void changePassword_WithInvalidData_ReturnsStatus406(ChangePasswordRequest request) {
        requestCommons.assertPutRequest(API_CHANGE_PASSWORD, request, FailureResponse.class, this::assertInvalidData);
    }

    @Test
    void changePassword_WithClientNotExists_ReturnsStatus404() {
        var request = new ChangePasswordRequest(1, 1234, "12345678900", "123456", "654321");
        requestCommons.assertPutRequest(API_CHANGE_PASSWORD, request, FailureResponse.class, this::assertClientNotExists);
    }

    @Test
    void activateAccount_WithClientDeactivated_ReturnsStatus409() {
        var request = new ActivateAccountRequest("12345678903", 1, 1235);
        requestCommons.assertPostRequest(API_CHANGE_PASSWORD, request, FailureResponse.class, this::assertClientDeactivatedExists);
    }

    @Test
    void activateAccount_WithAccountNotExists_ReturnsStatus404() {
        var request = new ActivateAccountRequest("12345678988", 2, 1235);
        requestCommons.assertPostRequest(API_CHANGE_PASSWORD, request, FailureResponse.class, this::assertAccountNotExists);
    }

    @Test
    void activateAccount_WithAccountAlreadyActive_ReturnsStatus409() {
        var request = new ActivateAccountRequest("12345678901", 1, 1234);
        requestCommons.assertPostRequest(API_CHANGE_PASSWORD, request, FailureResponse.class, this::assertAccountAlreadyActive);
    }

    private static Stream<Arguments> provideInvalidData() {
        return Stream.of(
                Arguments.of(new ChangePasswordRequest(null, 123456, "12345678901", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(0, 123456, "12345678901", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(-1, 123456, "12345678901", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, null, "12345678901", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 0, "12345678901", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, -233, "12345678901", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, null, "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "999", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "9999999999999", "123456", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "12345678901", null, "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "12345678901", "", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "12345678901", "12345", "876543")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "12345678901", "123456", null)),
                Arguments.of(new ChangePasswordRequest(1, 123456, "12345678901", "123456", "")),
                Arguments.of(new ChangePasswordRequest(1, 123456, "12345678901", "123456", "12345"))
        );
    }
}