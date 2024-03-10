package silva.daniel.project.app.domain.client;

import br.net.silva.business.value_object.output.NewAccountByNewClientResponseSuccess;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import org.springframework.stereotype.Service;
import silva.daniel.project.app.config.FluxComponent;

@Service
public class ClientService {

    private final FluxComponent fluxComponent;

    public ClientService(FluxComponent fluxComponent) {
        this.fluxComponent = fluxComponent;
    }

    public NewAccountByNewClientResponseSuccess createNewClient(ClientRequestDTO request) {
        var source = new Source(new NewAccountByNewClientResponseSuccess(), request);
        try {
            fluxComponent.fluxCreateNewClient().exec(source);
            return (NewAccountByNewClientResponseSuccess) source.output();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
