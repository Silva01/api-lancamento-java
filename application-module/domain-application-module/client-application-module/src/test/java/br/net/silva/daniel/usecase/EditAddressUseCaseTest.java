package br.net.silva.daniel.usecase;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.repository.ApplicationBaseRepository;
import br.net.silva.daniel.repository.ParamRepository;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.EditAddressInput;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EditAddressUseCaseTest {

    private EditAddressUseCase editAddressUseCase;

    @Mock
    private ApplicationBaseRepository<ClientOutput> baseRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(baseRepository.findById(any(ParamRepository.class))).thenReturn(Optional.of(buildClient()));
        when(baseRepository.save(any(ClientOutput.class))).thenReturn(buildClient());

        this.editAddressUseCase = new EditAddressUseCase(baseRepository);
    }

    @Test
    void shouldEditAddressWithSuccess() throws GenericException {
        var editAddressInput = new EditAddressInput(
                "22233344455",
                "Rua 2",
                "1234",
                "nao tem",
                "Bairro 2",
                "Estado 2",
                "Cidade 2",
                "11111111"
        );
        var source = new Source(EmptyOutput.INSTANCE, editAddressInput);

        var response = assertDoesNotThrow(() -> editAddressUseCase.exec(source));
        assertNotNull(response);

        verify(baseRepository, times(1)).findById(editAddressInput);
        verify(baseRepository, times(1)).save(any(ClientOutput.class));
    }

    private ClientOutput buildClient() {
        var address = new AddressOutput("Rua 1", "1234", "nao tem", "Bairro 1",  "Estado 1", "Cidade 1", "11111111");
        return new ClientOutput("1", "22233344455", "Daniel", "22344445555", true, address);
    }

}