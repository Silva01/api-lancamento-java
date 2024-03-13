package br.net.silva.daniel.integrations.test.flux;

import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.integrations.test.interfaces.AbstractBuilder;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.usecase.EditAddressUseCase;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.EditAddressInput;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FluxEditAddressOfClientTest extends AbstractBuilder {

    private UseCase<ClientOutput> editAddressUseCase;

    private UseCase<ClientOutput> findClientUseCase;

    private IValidations clientExistValidation;

    @Mock
    private Repository<Optional<ClientOutput>> findClientRepository;

    @Mock
    private Repository<ClientOutput> findClientByCpfRepository;

    @Mock
    private Repository<ClientOutput> saveClientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(findClientRepository.exec(any())).thenReturn(Optional.of(buildMockClient(true)));
        when(findClientByCpfRepository.exec(any())).thenReturn(buildMockClient(true));
        when(saveClientRepository.exec(any(ClientOutput.class))).thenReturn(buildMockClientEdited());


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

        verify(saveClientRepository, times(1)).exec(any(ClientOutput.class));
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
        when(saveClientRepository.exec(any(ClientOutput.class))).thenThrow(new NullPointerException("Generic Error"));
        var editAddressInput = new EditAddressInput("99988877766", "Rua 2", "Bairro 2", "Cidade 2", "Teste", "DF", "Brasilia", "44444-999");
        var source = new Source(EmptyOutput.INSTANCE, editAddressInput);

        Queue<UseCase<?>> useCaseQueue = new LinkedList<>();
        useCaseQueue.add(editAddressUseCase);

        List<IValidations> validations = new ArrayList<>();
        validations.add(clientExistValidation);

        var facade = new GenericFacadeDelegate<>(useCaseQueue, validations);
        var exceptionResponse = assertThrows(GenericException.class, () -> facade.exec(source));

        assertEquals("Generic Error", exceptionResponse.getMessage());

        verify(saveClientRepository, times(1)).exec(any(ClientOutput.class));
        verify(findClientByCpfRepository, times(1)).exec(anyString());
        verify(findClientRepository, times(1)).exec(anyString());
    }

    protected ClientOutput buildMockClientEdited() {
        var address = new AddressOutput("Rua 2", "Bairro 2", "Cidade 2", "Teste", "DF", "Brasilia", "44444-999");
        return new ClientOutput("abcd", "99988877766", "Daniel", "6122223333", true, address);
    }

}
