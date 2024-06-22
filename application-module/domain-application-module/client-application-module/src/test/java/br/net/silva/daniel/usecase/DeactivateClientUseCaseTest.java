package br.net.silva.daniel.usecase;

import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.value_object.input.DeactivateClient;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeactivateClientUseCaseTest {

    private DeactivateClientUseCase deactivateClientUseCase;

    private GenericResponseMapper facotry;

    @Mock
    private ApplicationBaseGateway<ClientOutput> baseRepository;


    @BeforeEach
    void setUp() {
        facotry = new GenericResponseMapper(Collections.emptyList());
        deactivateClientUseCase = new DeactivateClientUseCase(baseRepository, facotry);
    }

    @Test
    void mustDeactivateClientWithSuccess() throws GenericException {
        when(baseRepository.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildClient(true)));
        when(baseRepository.save(any(ClientOutput.class))).thenReturn(buildClient(false));

        var deactivateClient = new DeactivateClient("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, deactivateClient);
        deactivateClientUseCase.exec(source);
        Mockito.verify(baseRepository, Mockito.times(1)).findById(deactivateClient);
        Mockito.verify(baseRepository, Mockito.times(1)).save(any(ClientOutput.class));
    }

    @Test
    void mustErrorClientNotExistsWhenTryDeactivateClient() {
        when(baseRepository.findById(any(ParamGateway.class))).thenReturn(Optional.empty());
        var deactivateClient = new DeactivateClient("99988877766");
        var source = new Source(EmptyOutput.INSTANCE, deactivateClient);
        var exceptionReponse = assertThrows(GenericException.class, () -> deactivateClientUseCase.exec(source));

        assertEquals("Client not exists in database", exceptionReponse.getMessage());
    }

    private ClientOutput buildClient(boolean status) {
        var address = new AddressOutput("Rua 1", "Bairro 1", "Cidade 1", "Flores", "DF", "Brasilia", "44444-555");
        return new ClientOutput("abcd", "99988877766", "Daniel", "6122223333", status, address);
    }

}