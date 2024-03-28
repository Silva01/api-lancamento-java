package br.net.silva.daniel.build;

import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ClientNotExistsValidateBuilderTest {

    @Mock
    private FindApplicationBaseGateway<ClientOutput> findClientRepository;

    @Test
    void createValidate_WithValidParameters_ReturnsValidationInstance() {
        var validation = new ClientNotExistsValidateBuilder()
                .withRepository(findClientRepository)
                .build();

        assertThat(validation).isNotNull();
    }

    @Test
    void createValidate_WithRepositoryNull_ReturnsException() {
        assertThatThrownBy(() -> new ClientNotExistsValidateBuilder().withRepository(null).build())
                .isInstanceOf(NullPointerException.class)
                .hasMessage("UseCase is required");
    }

}