package silva.daniel.project.app.integration.web;

import br.net.silva.business.value_object.output.NewAccountResponse;
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
import silva.daniel.project.app.domain.account.request.NewAccountRequest;
import silva.daniel.project.app.domain.client.FailureResponse;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class CreateAccountControllerIT extends MysqlTestContainer implements IntegrationAssertCommons {

    private static final String API_CREATE_NEW_ACCOUNT = "/api/account";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private RequestIntegrationCommons requestCommons;

    @Test
    void createAccount_WithValidData_ReturnsSuccess() {
        var request = new NewAccountRequest("12345678988", 1, "123456");
        var response = restTemplate.postForEntity(API_CREATE_NEW_ACCOUNT, request, NewAccountResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var sut = repository.findByCpf(request.cpf());
        assertThat(sut).isPresent();
        assertThat(sut.get().isActive()).isTrue();
        assertThat(sut.get().getKeys().getBankAgencyNumber()).isEqualTo(request.agencyNumber());
        assertThat(sut.get().getKeys().getNumber()).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidData")
    void createAccount_WithInvalidData_ReturnsStatus406(NewAccountRequest request) {
        requestCommons.assertPostRequest(API_CREATE_NEW_ACCOUNT, request, FailureResponse.class, this::assertInvalidData);
    }

    @Test
    void createAccount_WithClientNotExists_ReturnsStatus404() {
        var request = new NewAccountRequest("12345678999", 1, "123456");
        requestCommons.assertPostRequest(API_CREATE_NEW_ACCOUNT, request, FailureResponse.class, this::assertClientNotExists);
    }

    private static Stream<Arguments> provideInvalidData() {
        return Stream.of(
                Arguments.of(new NewAccountRequest("123456789", 1, "123456")),
                Arguments.of(new NewAccountRequest("1234567898888888", 1, "123456")),
                Arguments.of(new NewAccountRequest("", 1, "123456")),
                Arguments.of(new NewAccountRequest(null, 1, "123456")),
                Arguments.of(new NewAccountRequest("12345678988", 0, "123456")),
                Arguments.of(new NewAccountRequest("12345678988", -1, "123456")),
                Arguments.of(new NewAccountRequest("12345678988", 1, "123"))
        );
    }
}