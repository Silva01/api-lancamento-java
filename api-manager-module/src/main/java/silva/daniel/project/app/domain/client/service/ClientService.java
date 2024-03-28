package silva.daniel.project.app.domain.client.service;

import br.net.silva.business.value_object.output.NewAccountByNewClientResponseSuccess;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.ActivateClient;
import br.net.silva.daniel.value_object.input.AddressRequestDTO;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import br.net.silva.daniel.value_object.input.DeactivateClient;
import br.net.silva.daniel.value_object.input.EditAddressInput;
import br.net.silva.daniel.value_object.input.EditClientInput;
import br.net.silva.daniel.value_object.input.FindClientByCpf;
import br.net.silva.daniel.value_object.output.ClientOutput;
import br.net.silva.daniel.value_object.output.GetInformationClientResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silva.daniel.project.app.domain.client.request.ClientRequest;
import silva.daniel.project.app.service.FluxService;

@Service
public class ClientService {

    private final FluxService fluxService;

    public ClientService(FluxService fluxService) {
        this.fluxService = fluxService;
    }

    @Transactional
    public NewAccountByNewClientResponseSuccess createNewClient(ClientRequestDTO request) throws Exception {
        var source = new Source(new NewAccountByNewClientResponseSuccess(), request);
        fluxService.fluxCreateNewClient().exec(source);
        return (NewAccountByNewClientResponseSuccess) source.output();
    }

    @Transactional
    public void updateClient(EditClientInput request) throws Exception {
        var source = new Source(EmptyOutput.INSTANCE, request);
        fluxService.fluxUpdateClient().exec(source);
    }

    @Transactional
    public void deactivateClient(DeactivateClient deactivateClient) throws Exception {
        final var source = new Source(EmptyOutput.INSTANCE, deactivateClient);
        fluxService.fluxDeactivateClient().exec(source);
    }

    @Transactional
    public void activateClient(ActivateClient deactivateClient) throws Exception {
        final var source = new Source(EmptyOutput.INSTANCE, deactivateClient);
        fluxService.fluxActivateClient().exec(source);
    }

    @Transactional
    public void updateAddress(EditAddressInput addressInput) throws Exception {
        final var source = new Source(EmptyOutput.INSTANCE, addressInput);
        fluxService.fluxUpdateAddress().exec(source);
    }

    public GetInformationClientResponse getClientByCpf(String cpf) throws Exception {
        final var clientByCpf = new FindClientByCpf(cpf);
        final var source = new Source(new GetInformationClientResponse(), clientByCpf);
        fluxService.fluxFindClient().exec(source);
        return (GetInformationClientResponse) source.output();
    }
}
