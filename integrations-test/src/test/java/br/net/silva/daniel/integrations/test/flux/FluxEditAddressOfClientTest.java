package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.usecase.EditAddressUseCase;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.value_object.Address;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.EditAddressInput;
import br.net.silva.daniel.value_object.input.EditClientInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FluxEditAddressOfClientTest extends AbstractBuilder {

    private UseCase<ClientDTO> editAddressUseCase;

    private UseCase<ClientDTO> findClientUseCase;

    private IValidations clientExistValidation;

    @Mock
    private Repository<Optional<Client>> findClientRepository;

    @Mock
    private Repository<Client> findClientByCpfRepository;

    @Mock
    private Repository<Client> saveClientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(findClientRepository.exec(any())).thenReturn(Optional.of(buildMockClient(true)));
        when(findClientByCpfRepository.exec(any())).thenReturn(buildMockClient(true));
        when(saveClientRepository.exec(any(Client.class))).thenReturn(buildMockClientEdited());


        // UseCase
        this.findClientUseCase = new FindClientUseCase(this.findClientRepository, buildFactoryResponse());
        this.editAddressUseCase = new EditAddressUseCase(this.findClientByCpfRepository, this.saveClientRepository);

        // Validations
        clientExistValidation = new ClientExistsValidate(findClientUseCase);
    }

    @Test
    void shouldEditAddressOfClientWithSuccess() {
        var editAddressInput = new EditAddressInput("99988877766", "Rua 2", "Bairro 2", "Cidade 2", "Teste", "DF", "Brasilia", "44444-999");
        var source = new Source(EmptyOutput.INSTANCE, editAddressInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(editAddressUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        assertDoesNotThrow(() -> facade.exec(source));

        verify(saveClientRepository, times(1)).exec(any(Client.class));
        verify(findClientByCpfRepository, times(1)).exec(anyString());
        verify(findClientRepository, times(1)).exec(anyString());
    }

    @Test
    void shouldErrorEditAddressClientNotExists() {
        when(findClientByCpfRepository.exec(any())).thenReturn(null);
        var editAddressInput = new EditAddressInput("99988877766", "Rua 2", "Bairro 2", "Cidade 2", "Teste", "DF", "Brasilia", "44444-999");
        var source = new Source(EmptyOutput.INSTANCE, editAddressInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(editAddressUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));

        assertEquals("Generic Error", exceptionResponse.getMessage());

        verify(saveClientRepository, times(0)).exec(any(Client.class));
        verify(findClientByCpfRepository, times(1)).exec(anyString());
        verify(findClientRepository, times(1)).exec(anyString());
    }

    @Test
    void shouldErrorEditAddressClientNull() {
        when(findClientRepository.exec(any())).thenReturn(Optional.empty());
        var editAddressInput = new EditAddressInput("99988877766", "Rua 2", "Bairro 2", "Cidade 2", "Teste", "DF", "Brasilia", "44444-999");
        var source = new Source(EmptyOutput.INSTANCE, editAddressInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(editAddressUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));

        assertEquals("Client not exists in database", exceptionResponse.getMessage());

        verify(saveClientRepository, times(0)).exec(any(Client.class));
        verify(findClientByCpfRepository, times(0)).exec(anyString());
        verify(findClientRepository, times(1)).exec(anyString());
    }

    @Test
    void shouldErrorWhenEditAddressNullPointer() {
        when(saveClientRepository.exec(any(Client.class))).thenReturn(null);
        var editAddressInput = new EditAddressInput("99988877766", "Rua 2", "Bairro 2", "Cidade 2", "Teste", "DF", "Brasilia", "44444-999");
        var source = new Source(EmptyOutput.INSTANCE, editAddressInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(editAddressUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));

        assertEquals("Generic Error", exceptionResponse.getMessage());

        verify(saveClientRepository, times(1)).exec(any(Client.class));
        verify(findClientByCpfRepository, times(1)).exec(anyString());
        verify(findClientRepository, times(1)).exec(anyString());
    }

    protected Client buildMockClientEdited() {
        var address = new Address("Rua 2", "Bairro 2", "Cidade 2", "Teste", "DF", "Brasilia", "44444-999");
        return new Client("abcd", "99988877766", "Daniel", "6122223333", true, address);
    }

}
