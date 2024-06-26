package br.net.silva.daniel.usecase;

import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.value_object.input.FindClientByCpf;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class FindClientUseCaseTest {

    @Mock
    private FindApplicationBaseGateway<ClientOutput> findClientRepository;

    private GenericResponseMapper factory;

    private FindClientUseCase findClientUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new GenericResponseMapper(Collections.emptyList());
        when(findClientRepository.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildClient()));
        this.findClientUseCase = new FindClientUseCase(findClientRepository, factory);
    }

    @Test
    void must_looking_for_client_in_database_with_success() throws GenericException {
        var findClientByCpf = new FindClientByCpf("22233344455");
        var source = new Source(EmptyOutput.INSTANCE, findClientByCpf);
        var clientDto = findClientUseCase.exec(source);

        assertNotNull(source.output());
        assertNotNull(clientDto);
        verify(findClientRepository, times(1)).findById(findClientByCpf);
    }

    @Test
    void must_looking_for_client_in_database_what_not_exists() {
        var findClientByCpf = new FindClientByCpf("22233344455");
        var source = new Source(EmptyOutput.INSTANCE, findClientByCpf);
        when(findClientRepository.findById(any(ParamGateway.class))).thenReturn(Optional.empty());
        Assertions.assertThrows(ClientNotExistsException.class, () -> findClientUseCase.exec(source));
    }

    private ClientOutput buildClient() {
        var address = new AddressOutput("Rua 1", "1234", "nao tem", "Bairro 1",  "Estado 1", "Cidade 1", "11111111");
        return new ClientOutput("1", "22233344455", "Daniel", "22344445555", true, address);
    }

}