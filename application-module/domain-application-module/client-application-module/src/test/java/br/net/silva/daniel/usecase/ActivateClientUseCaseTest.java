package br.net.silva.daniel.usecase;

import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ActivateClientUseCaseTest {

    private ActivateClientUseCase activateClientUseCase;

    @Mock
    private ApplicationBaseGateway<ClientOutput> baseRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        activateClientUseCase = new ActivateClientUseCase(baseRepository);
    }

    @Test
    void mustActivateClientWithSucess() throws GenericException {
        when(baseRepository.findById(any(ParamGateway.class))).thenReturn(Optional.of(createClient()));
        when(baseRepository.save(any(ClientOutput.class))).thenReturn(createClient());
        var dto = new ClientRequestDTO("1234", "00099988877", "Daniel", "61933334444", true, 1234, null);
        var source = new Source(EmptyOutput.INSTANCE, dto);
        activateClientUseCase.exec(source);

        var mockResponse = (EmptyOutput) source.output();
        assertNotNull(mockResponse);

        verify(baseRepository, Mockito.times(1)).findById(any(ParamGateway.class));
        verify(baseRepository, Mockito.times(1)).save(any(ClientOutput.class));
    }

    private ClientOutput createClient() {
        var address = new AddressOutput("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556");
        return new ClientOutput("1234", "00099988877", "Daniel", "61933334444", true, address);
    }

}