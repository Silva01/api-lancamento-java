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
import silva.daniel.project.app.domain.client.FailureResponse;
import silva.daniel.project.app.domain.client.entity.repository.ClientRepository;
import silva.daniel.project.app.domain.client.request.AddressRequest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class UpdateAddressControllerIT extends MysqlTestContainer implements IntegrationAssertCommons {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientRepository repository;

    @Test
    void editClient_WithValidData_Returns201AndAccountData() {
        var request = new AddressRequest("12345678901", "street", "number", "complement", "neighborhood", "city", "state", "zipCode");

        var httpEntity = new HttpEntity<>(request);
        var sut = restTemplate.exchange("/clients/address", HttpMethod.PUT, httpEntity, Void.class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

        var client = repository.findByCpf("12345678901");
        assertThat(client).isPresent();
        assertThat(client.get().getAddress().getStreet()).isEqualTo(request.street());
        assertThat(client.get().getAddress().getNumber()).isEqualTo(request.number());
        assertThat(client.get().getAddress().getComplement()).isEqualTo(request.complement());
        assertThat(client.get().getAddress().getNeighborhood()).isEqualTo(request.neighborhood());
        assertThat(client.get().getAddress().getCity()).isEqualTo(request.city());
        assertThat(client.get().getAddress().getState()).isEqualTo(request.state());
        assertThat(client.get().getAddress().getZipCode()).isEqualTo(request.zipCode());
    }

    @ParameterizedTest
    @MethodSource("provideAddressRequestInvalidData")
    void editClient_WithInvalidData_ReturnsStatus406(AddressRequest request) {
        final var httpEntity = new HttpEntity<>(request);
        var sut = restTemplate.exchange("/clients/address", HttpMethod.PUT, httpEntity, FailureResponse.class);
        assertInvalidData(sut);
    }

    @Test
    void editClient_WithCpfNotExists_ReturnsStatus404() {
        var request = new AddressRequest("12345600000", "street", "number", "complement", "neighborhood", "city", "state", "zipCode");
        final var httpEntity = new HttpEntity<>(request);
        var sut = restTemplate.exchange("/clients/address", HttpMethod.PUT, httpEntity, FailureResponse.class);
        assertClientNotExists(sut);
    }

    @Test
    void editClient_WithCpfDeactivated_ReturnsStatus409() {
        var request = new AddressRequest("12345678903", "street", "number", "complement", "neighborhood", "city", "state", "zipCode");
        final var httpEntity = new HttpEntity<>(request);
        var sut = restTemplate.exchange("/clients/address", HttpMethod.PUT, httpEntity, FailureResponse.class);
        assertClientAlreadyDeactivatedExists(sut);
    }

    private static Stream<Arguments> provideAddressRequestInvalidData() {
        return Stream.of(
                Arguments.of(new AddressRequest("88899988800", "", "number", "complement", "neighborhood", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "", "complement", "neighborhood", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("", "street", "number", "complement", "neighborhood", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", "", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", "city", "", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", "city", "state", "")),
                Arguments.of(new AddressRequest("88899988800", null, "number", "complement", "neighborhood", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", null, "complement", "neighborhood", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest(null, "street", "number", "complement", "neighborhood", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", null, "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", null, "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", "city", null, "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", "city", "state", null))
        );
    }

}