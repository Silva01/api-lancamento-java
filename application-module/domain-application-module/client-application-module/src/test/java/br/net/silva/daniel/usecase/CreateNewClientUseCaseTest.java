package br.net.silva.daniel.usecase;

import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.gateway.SaveApplicationBaseGateway;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.AddressRequestDTO;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class CreateNewClientUseCaseTest {

    @Mock
    private SaveApplicationBaseGateway<ClientOutput> saveRepository;

    private CreateNewClientUseCase createNewClientUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(saveRepository.save(any(ClientOutput.class))).thenReturn(createClient());
        createNewClientUseCase = new CreateNewClientUseCase(saveRepository, new GenericResponseMapper(Collections.emptyList()));
    }

    @Test
    void testShouldCreateANewClientWithSuccess() throws Exception {
        var newRequestClient = new ClientRequestDTO(
                "1234",
                "00099988877",
                "Daniel",
                "61933334444",
                true,
                1234,
                new AddressRequestDTO("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556")
        );

        var source = new Source(EmptyOutput.INSTANCE, newRequestClient);

        createNewClientUseCase.exec(source);

        verify(saveRepository, times(1)).save(any(ClientOutput.class));
    }

    @Test
    void testShouldCreateANewClientWithErrorExistsClient() {
        // Arrange
        var newRequestClient = new ClientRequestDTO(
                "1234",
                "00099988877",
                "Daniel",
                "61933334444",
                true,
                1234,
                new AddressRequestDTO("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556")
        );

        var source = new Source(EmptyOutput.INSTANCE, newRequestClient);


        // Mock the repository
        when(saveRepository.save(any())).thenThrow(new RuntimeException("Exists client"));

        // Act & Assert
        assertThrows(ExistsClientRegistredException.class, () -> {
            createNewClientUseCase.exec(source);
        });

        // Verify that saveRepository.exec was called exactly once
        verify(saveRepository, times(1)).save(any(ClientOutput.class));
    }

    private ClientOutput createClient() {
        var address = new AddressOutput("Street 1", "1234", "Brazil", "Brasilia", "DF", "Brasilia", "12344-556");
        return new ClientOutput("1234", "00099988877", "Daniel", "61933334444", true, address);
    }

}