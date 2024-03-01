package br.net.silva.daniel.usecase;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateClientByDtoFactory;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ActivateClientUseCaseTest {

    private ActivateClientUseCase activateClientUseCase;

    private CreateClientByDtoFactory factory;

    @Mock
    private Repository<ClientOutput> activateClientRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        activateClientUseCase = new ActivateClientUseCase(activateClientRepository);
        this.factory = new CreateClientByDtoFactory();
    }

    @Test
    void mustActivateClientWithSucess() throws GenericException {
        when(activateClientRepository.exec(Mockito.anyString())).thenReturn(createClient());
        var dto = new ClientRequestDTO("1234", "00099988877", "Daniel", "61933334444", true, 1234, null);
        var source = new Source(EmptyOutput.INSTANCE, dto);
        activateClientUseCase.exec(source);

        var mockResponse = (EmptyOutput) source.output();
        assertNotNull(mockResponse);

        verify(activateClientRepository, Mockito.times(1)).exec(Mockito.anyString());
    }

    private ClientOutput createClient() {
        var address = new AddressOutput("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556");
        return new ClientOutput("1234", "00099988877", "Daniel", "61933334444", true, address);
    }

}