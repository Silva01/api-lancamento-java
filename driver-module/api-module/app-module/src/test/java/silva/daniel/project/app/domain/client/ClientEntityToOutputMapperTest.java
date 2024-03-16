package silva.daniel.project.app.domain.client;

import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ClientEntityToOutputMapperTest {

    private ClientEntityToOutputMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ClientEntityToOutputMapper();
    }

    @Test
    void convertClient_FromOutput_ReturnsClient() {
        final var address = new AddressOutput("Rua 1", "123", "Apto 1", "Bairro 1", "Cidade 1", "Estado 1", "12345678");
        final var output = new ClientOutput("1", "12345678901", "Daniel", "1234567890", true, address);

        final var sut = mapper.mapTo(output);
        assertThat(sut).isNotNull();
        assertThat(sut.getId()).isNull();
        assertThat(sut.getAggregateId()).isEqualTo(output.id());
        assertThat(sut.getCpf()).isEqualTo(output.cpf());
        assertThat(sut.getName()).isEqualTo(output.name());
        assertThat(sut.getTelephone()).isEqualTo(output.telephone());
        assertThat(sut.isActive()).isEqualTo(output.active());
        assertThat(sut.getAddress().getStreet()).isEqualTo(output.address().street());
        assertThat(sut.getAddress().getNumber()).isEqualTo(output.address().number());
        assertThat(sut.getAddress().getComplement()).isEqualTo(output.address().complement());
        assertThat(sut.getAddress().getNeighborhood()).isEqualTo(output.address().neighborhood());
        assertThat(sut.getAddress().getCity()).isEqualTo(output.address().city());
        assertThat(sut.getAddress().getState()).isEqualTo(output.address().state());
        assertThat(sut.getAddress().getZipCode()).isEqualTo(output.address().zipCode());
    }

    @Test
    void convertClient_FromEntity_ReturnsOutput() {
        final var address = new Address();
        address.setStreet("Rua 1");
        address.setNumber("123");
        address.setComplement("Apto 1");
        address.setNeighborhood("Bairro 1");
        address.setCity("Cidade 1");
        address.setState("Estado 1");
        address.setZipCode("12345678");


        final var entity = new Client();
        entity.setAggregateId("1");
        entity.setCpf("12345678901");
        entity.setName("Daniel");
        entity.setTelephone("1234567890");
        entity.setActive(true);
        entity.setAddress(address);

        final var sut = mapper.mapTo(entity);
        assertThat(sut).isNotNull();
        assertThat(sut.id()).isEqualTo(entity.getAggregateId());
        assertThat(sut.cpf()).isEqualTo(entity.getCpf());
        assertThat(sut.name()).isEqualTo(entity.getName());
        assertThat(sut.telephone()).isEqualTo(entity.getTelephone());
        assertThat(sut.active()).isEqualTo(entity.isActive());
        assertThat(sut.address().street()).isEqualTo(entity.getAddress().getStreet());
        assertThat(sut.address().number()).isEqualTo(entity.getAddress().getNumber());
        assertThat(sut.address().complement()).isEqualTo(entity.getAddress().getComplement());
        assertThat(sut.address().neighborhood()).isEqualTo(entity.getAddress().getNeighborhood());
        assertThat(sut.address().city()).isEqualTo(entity.getAddress().getCity());
        assertThat(sut.address().state()).isEqualTo(entity.getAddress().getState());
        assertThat(sut.address().zipCode()).isEqualTo(entity.getAddress().getZipCode());
    }

}