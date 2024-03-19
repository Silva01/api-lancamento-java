package silva.daniel.project.app.domain.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import silva.daniel.project.app.commons.GeneratorCpf;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static silva.daniel.project.app.commons.ClientCommons.addressEntityMock;
import static silva.daniel.project.app.commons.ClientCommons.entityMock;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void createNewClient_WithValidData_ReturnsNewClientWithId() {
        var client = entityMock();
        var sut = repository.save(client);
        assertThat(sut).isNotNull();

        var newClient = entityManager.find(Client.class, sut.getCpf());
        assertThat(newClient).isNotNull();

        assertThat(newClient.getCpf()).isEqualTo(sut.getCpf());
        assertThat(newClient.getName()).isEqualTo(sut.getName());
        assertThat(newClient.getTelephone()).isEqualTo(sut.getTelephone());
        assertThat(newClient.isActive()).isEqualTo(sut.isActive());
        assertThat(newClient.getAddress()).isEqualTo(sut.getAddress());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidClients")
    void createNewClient_WithInvalidData_ReturnsException(Client client) {
        assertThatThrownBy(() -> repository.save(client))
                .isInstanceOf(RuntimeException.class);
    }

    private static Stream<Arguments> provideInvalidClients() {
        return Stream.of(
                Arguments.of(new Client(null, null, null, null, true, null)),
                Arguments.of(new Client(GeneratorCpf.start(), null, "Daniel Silva", "123456789", true, addressEntityMock())),
                Arguments.of(new Client(GeneratorCpf.start(), UUID.randomUUID().toString(), null, "123456789", true, addressEntityMock())),
                Arguments.of(new Client(GeneratorCpf.start(), UUID.randomUUID().toString(), "Daniel Silva", "", true, null)),
                Arguments.of(new Client(GeneratorCpf.start(), UUID.randomUUID().toString(), "Daniel Silva", "123456789", true, new Address(null, null, "home 1", "Test", "Ops", "BR", "OP", "12345678"))),
                Arguments.of(new Client(GeneratorCpf.start(), UUID.randomUUID().toString(), "Daniel Silva", "123456789", true, new Address(null, null, "home 1", "Test", "Ops", "BR", "OP", "12345678"))),
                Arguments.of(new Client(GeneratorCpf.start(), UUID.randomUUID().toString(), "Daniel Silva", "123456789", true, new Address(null, "123", null, "Test", "Ops", "BR", "OP", "12345678"))),
                Arguments.of(new Client(GeneratorCpf.start(), UUID.randomUUID().toString(), "Daniel Silva", "123456789", true, new Address(null, "123", "home 1", "Test", null, "BR", "OP", null))),
                Arguments.of(new Client(GeneratorCpf.start(), UUID.randomUUID().toString(), "Daniel Silva", "123456789", true, new Address(null, "123", "home 1", "Test", "Ops", null, "OP", "12345678"))),
                Arguments.of(new Client(GeneratorCpf.start(), UUID.randomUUID().toString(), "Daniel Silva", "123456789", true, new Address(null, "123", "home 1", "Test", "Ops", "BR", null, "12345678"))),
                Arguments.of(new Client(GeneratorCpf.start(), UUID.randomUUID().toString(), "Daniel Silva", "123456789", true, new Address(null, "123", "home 1", "Test", "Ops", "BR", "OP", null))),
                Arguments.of(new Client(GeneratorCpf.start(), UUID.randomUUID().toString(), "Daniel Silva", "123456789", true, new Address(null, null, null, null, null, null, null, null)))
        );
    }

}