package silva.daniel.project.app.domain.client;

import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import silva.daniel.project.app.mapper.Mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static silva.daniel.project.app.commons.ClientCommons.entityMock;
import static silva.daniel.project.app.commons.ClientCommons.outputMock;

@SpringBootTest(classes = ClientGatewayImpl.class)
class ClientGatewayImplTest {

    @Autowired
    private ClientGatewayImpl clientGatewayImpl;

    @MockBean
    private ClientRepository repository;

    @MockBean
    private Mapper<ClientOutput, Client> mapper;

    @Test
    void createNewClient_WithValidData_ReturnsNewClientWithId() {
        final var clientOutput = outputMock();
        final var clientEntityMock = entityMock();
        when(repository.save(any(Client.class))).thenReturn(clientEntityMock);
        when(mapper.mapTo(any(ClientOutput.class))).thenReturn(clientEntityMock);
        when(mapper.mapTo(any(Client.class))).thenReturn(clientOutput);

        final var sut = clientGatewayImpl.save(clientOutput);

        assertThat(sut).isNotNull();
        assertThat(sut.id()).isEqualTo(clientOutput.id());
        assertThat(sut.cpf()).isEqualTo(clientOutput.cpf());
        assertThat(sut.name()).isEqualTo(clientOutput.name());
        assertThat(sut.telephone()).isEqualTo(clientOutput.telephone());
        assertThat(sut.active()).isEqualTo(clientOutput.active());
        assertThat(sut.address()).isEqualTo(clientOutput.address());
    }

    @Test
    void createNewClient_WithClientExists_ReturnsError() {
        final var clientOutput = outputMock();
        final var clientEntityMock = entityMock();
        when(repository.save(any(Client.class))).thenThrow(new DataIntegrityViolationException("Key Duplicated in Database"));
        when(mapper.mapTo(any(ClientOutput.class))).thenReturn(clientEntityMock);

        assertThatThrownBy(() -> clientGatewayImpl.save(clientOutput))
                .isInstanceOf(DataIntegrityViolationException.class)
                .hasMessage("Key Duplicated in Database");
    }

}