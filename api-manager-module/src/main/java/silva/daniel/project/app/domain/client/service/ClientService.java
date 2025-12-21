package silva.daniel.project.app.domain.client.service;

import br.net.silva.business.value_object.output.NewAccountByNewClientResponseSuccess;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.Output;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import br.net.silva.daniel.value_object.input.ActivateClient;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import br.net.silva.daniel.value_object.input.DeactivateClient;
import br.net.silva.daniel.value_object.input.EditAddressInput;
import br.net.silva.daniel.value_object.input.EditClientInput;
import br.net.silva.daniel.value_object.input.FindClientByCpf;
import br.net.silva.daniel.value_object.output.GetInformationClientResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    private final ClientFacade clientFacade;

    public ClientService(ClientFacade clientFacade) {
        this.clientFacade = clientFacade;
    }

    @Transactional
    public Output createNewClient(ClientRequestDTO request) throws DomainGenericException {
        var source = new Source(new NewAccountByNewClientResponseSuccess(), request);
        clientFacade.createNewClient().exec(source);
        return source.output();
    }

    @Transactional
    public void updateClient(EditClientInput request) throws DomainGenericException {
        var source = new Source(EmptyOutput.INSTANCE, request);
        clientFacade.updateClient().exec(source);
    }

    @Transactional
    public void deactivateClient(DeactivateClient deactivateClient) throws DomainGenericException {
        final var source = new Source(EmptyOutput.INSTANCE, deactivateClient);
        clientFacade.deactivateClient().exec(source);
    }

    @Transactional
    public void activateClient(ActivateClient activateClient) throws DomainGenericException {
        final var source = new Source(EmptyOutput.INSTANCE, activateClient);
        clientFacade.activateClient().exec(source);
    }

    @Transactional
    public void updateAddress(EditAddressInput addressInput) throws DomainGenericException {
        final var source = new Source(EmptyOutput.INSTANCE, addressInput);
        clientFacade.updateAddress().exec(source);
    }

    public Output getClientByCpf(String cpf) throws DomainGenericException {
        final var clientByCpf = new FindClientByCpf(cpf);
        final var source = new Source(new GetInformationClientResponse(), clientByCpf);
        clientFacade.findClient().exec(source);
        return source.output();
    }
}
