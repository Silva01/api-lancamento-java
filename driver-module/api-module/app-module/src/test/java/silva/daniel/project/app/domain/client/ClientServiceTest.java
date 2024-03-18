package silva.daniel.project.app.domain.client;

import br.net.silva.business.value_object.output.NewAccountByNewClientResponseSuccess;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.AddressRequestDTO;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import silva.daniel.project.app.config.FluxComponent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService service;

    @Mock
    private FluxComponent fluxComponent;

    @Mock
    private GenericFacadeDelegate facade;

    @Test
    void createNewClient_WithValidData_ReturnsNewAccountByNewClientResponseSuccess() throws Exception {
        when(fluxComponent.fluxCreateNewClient()).thenReturn(facade);
        doAnswer((argumentsOnMock) -> {
            var source = (Source) argumentsOnMock.getArgument(0);
            final var response = (NewAccountByNewClientResponseSuccess) source.output();
            response.setAgency(1);
            response.setAccountNumber(2);
            response.setProvisionalPassword("123456");
            return null;
        }).when(facade).exec(any(Source.class));

        final var requestDto = new ClientRequestDTO(
                null,
                "12345678901",
                "Daniel",
                "61999999999",
                true,
                123,
                new AddressRequestDTO(
                        "Rua 1",
                        "2",
                        "Cidade 1",
                        "bairro 1",
                        "DF",
                        "Brasilia",
                        "12345678"
                )
        );

        final var sut = service.createNewClient(requestDto);
        assertThat(sut).isNotNull();
        assertThat(sut.getAgency()).isEqualTo(1);
        assertThat(sut.getAccountNumber()).isEqualTo(2);
        assertThat(sut.getProvisionalPassword()).isEqualTo("123456");
    }

}