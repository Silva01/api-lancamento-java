package silva.daniel.project.app.integration.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import silva.daniel.project.app.commons.MysqlTestContainer;
import silva.daniel.project.app.domain.client.ActivateClient;
import silva.daniel.project.app.domain.client.ClientRepository;
import silva.daniel.project.app.domain.client.FailureResponse;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ActivateClientControllerIT extends MysqlTestContainer {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientRepository repository;

    @Test
    void deactivateClient_WithValidData_ReturnsStatus200() {
        final var request = new ActivateClient("12345678903");
        var sut = restTemplate.postForEntity("/clients/activate", request, Void.class);
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.OK);

        var client = repository.findByCpf("12345678903");
        assertThat(client).isPresent();
        assertThat(client.get().isActive()).isTrue();
    }

    @Test
    void deactivateClient_WithInvalidData_ReturnsStatus406() {
        final var requestEmpty = new ActivateClient("");
        var sutEmpty = restTemplate.postForEntity("/clients/activate", requestEmpty, FailureResponse.class);
        assertThat(sutEmpty.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
        assertThat(sutEmpty.getBody()).isNotNull();
        assertThat(sutEmpty.getBody().getMessage()).isEqualTo("Information is not valid");
        assertThat(sutEmpty.getBody().getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());

        final var requestNull = new ActivateClient(null);
        var sutNull = restTemplate.postForEntity("/clients/activate", requestNull, Void.class);
        assertThat(sutNull.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
        assertThat(sutEmpty.getBody()).isNotNull();
        assertThat(sutEmpty.getBody().getMessage()).isEqualTo("Information is not valid");
        assertThat(sutEmpty.getBody().getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE.value());
    }

    @Test
    void deactivateClient_WithNoExistsClient_ReturnsStatus404() {
        final var request = new ActivateClient("00000000000");
        var sutEmpty = restTemplate.postForEntity("/clients/activate", request, FailureResponse.class);
        assertThat(sutEmpty.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(sutEmpty.getBody()).isNotNull();
        assertThat(sutEmpty.getBody().getMessage()).isEqualTo("Client not exists in database");
        assertThat(sutEmpty.getBody().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void deactivateClient_WithAlreadyActivatedClient_ReturnsStatus406() {
        final var request = new ActivateClient("12345678904");
        var sutEmpty = restTemplate.postForEntity("/clients/activate", request, FailureResponse.class);
        assertThat(sutEmpty.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(sutEmpty.getBody()).isNotNull();
        assertThat(sutEmpty.getBody().getMessage()).isEqualTo("Client already activated");
        assertThat(sutEmpty.getBody().getStatusCode()).isEqualTo(HttpStatus.CONFLICT.value());
    }
}
