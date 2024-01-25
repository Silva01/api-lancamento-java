package br.net.silva.daniel.usecase;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Address;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.EditAddressInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EditAddressUseCaseTest {

    private EditAddressUseCase editAddressUseCase;

    @Mock
    private Repository<Client> findClientRepository;

    @Mock
    private Repository<Client> saveClientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(findClientRepository.exec(anyString())).thenReturn(buildClient());
        when(saveClientRepository.exec(any(Client.class))).thenReturn(buildClient());

        this.editAddressUseCase = new EditAddressUseCase(findClientRepository, saveClientRepository);
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

        verify(findClientRepository, times(1)).exec(editAddressInput.cpf());
        verify(saveClientRepository, times(1)).exec(any(Client.class));
    }

    private Client buildClient() {
        var address = new Address("Rua 1", "1234", "nao tem", "Bairro 1",  "Estado 1", "Cidade 1", "11111111");
        return new Client("1", "22233344455", "Daniel", "22344445555", true, address);
    }

}