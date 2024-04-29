package br.net.silva.daniel.usecase;

import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.EditAddressInput;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditAddressUseCaseTest {

    private EditAddressUseCase editAddressUseCase;

    @Mock
    private ApplicationBaseGateway<ClientOutput> baseRepository;

    @BeforeEach
    void setUp() {
        this.editAddressUseCase = new EditAddressUseCase(baseRepository);
    }

    @Test
    void shouldEditAddressWithSuccess() {
        when(baseRepository.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildClient()));
        when(baseRepository.save(any(ClientOutput.class))).thenReturn(buildClient());

        final var editAddressInput = mockAddress();
        var source = new Source(EmptyOutput.INSTANCE, editAddressInput);

        var response = assertDoesNotThrow(() -> editAddressUseCase.exec(source));
        assertNotNull(response);

        verify(baseRepository, times(1)).findById(editAddressInput);
        verify(baseRepository, times(1)).save(any(ClientOutput.class));
    }

    @Test
    void editAddress_WithClientNotExists_ThrowsException() {
        when(baseRepository.findById(any(ParamGateway.class))).thenReturn(Optional.empty());
        final var editAddressInput = mockAddress();
        final var source = new Source(EmptyOutput.INSTANCE, editAddressInput);

        assertThatThrownBy(() -> editAddressUseCase.exec(source))
                .isInstanceOf(ClientNotExistsException.class)
                .hasMessageContaining("Client not exists in database");

        verify(baseRepository, times(1)).findById(editAddressInput);
        verify(baseRepository, never()).save(any(ClientOutput.class));
    }

    private ClientOutput buildClient() {
        var address = new AddressOutput("Rua 1", "1234", "nao tem", "Bairro 1",  "Estado 1", "Cidade 1", "11111111");
        return new ClientOutput("1", "22233344455", "Daniel", "22344445555", true, address);
    }

    private static EditAddressInput mockAddress() {
        return new EditAddressInput(
                "22233344455",
                "Rua 2",
                "1234",
                "nao tem",
                "Bairro 2",
                "Estado 2",
                "Cidade 2",
                "11111111"
        );
    }

}