package silva.daniel.project.app.integration.web;

import org.jetbrains.annotations.NotNull;
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
import silva.daniel.project.app.domain.account.request.EditAgencyOfAccountRequest;
import silva.daniel.project.app.domain.client.FailureResponse;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class EditAgencyOfAccountControllerIT extends MysqlTestContainer implements IntegrationAssertCommons {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private RequestIntegrationCommons requestCommons;

    @Test
    void editAgencyOfAccount_WithValidData_ReturnsStatus200() {
        final var request = buildRequestBase();
        var httpEntity = new HttpEntity<>(request);
        var sut = restTemplate.exchange("/api/account/update/agency", HttpMethod.PUT, httpEntity, Void.class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

        var account = repository.findById(new AccountKey(request.accountNumber(), request.newAgencyNumber()));
        assertThat(account).isPresent();
        assertThat(account.get().getKeys().getBankAgencyNumber()).isEqualTo(request.newAgencyNumber());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void editAgencyOfAccount_WithInvalidData_ReturnsStatus406(EditAgencyOfAccountRequest request) {
        requestCommons.assertPutRequest("/api/account/update/agency", request, FailureResponse.class, this::assertInvalidData);
    }

    @Test
    void editAgencyOfAccount_WithClientNotExists_ReturnsStatus404() {
        final var request = new EditAgencyOfAccountRequest("12345678920", 1234, 1, 2);
        requestCommons.assertPutRequest("/api/account/update/agency", request, FailureResponse.class, this::assertClientNotExists);
    }

    @Test
    void editAgencyOfAccount_WithAccountNotExists_ReturnsStatus404() {
        final var request = new EditAgencyOfAccountRequest("12345678901", 9999, 1, 2);
        requestCommons.assertPutRequest("/api/account/update/agency", request, FailureResponse.class, this::assertAccountNotExists);
    }

    @Test
    void editAgencyOfAccount_WithNewAgencyAlreadyUsed_ReturnsStatus409() {
        final var request = new EditAgencyOfAccountRequest("12345678901", 1234, 1, 5);
        var httpEntity = new HttpEntity<>(request);
        var sut = restTemplate.exchange("/api/account/update/agency", HttpMethod.PUT, httpEntity, FailureResponse.class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(sut.getBody().getMessage()).isEqualTo(ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE.getMessage());
        assertThat(sut.getBody().getStatusCode()).isEqualTo(ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE.getStatusCode());
    }

    private static Stream<Arguments> provideInvalidData() {
        return Stream.of(
                Arguments.of(new EditAgencyOfAccountRequest(null, 1234, 1, 2)),
                Arguments.of(new EditAgencyOfAccountRequest("", 1234, 1, 2)),
                Arguments.of(new EditAgencyOfAccountRequest("12345678901", 1234, 1, null)),
                Arguments.of(new EditAgencyOfAccountRequest("12345678901", 1234, null, 2)),
                Arguments.of(new EditAgencyOfAccountRequest("12345678901", null, 1, 2)),
                Arguments.of(new EditAgencyOfAccountRequest(null, null, null, null))
        );
    }

    @NotNull
    private static EditAgencyOfAccountRequest buildRequestBase() {
        return new EditAgencyOfAccountRequest("12345678901", 1234, 1, 2);
    }
}
