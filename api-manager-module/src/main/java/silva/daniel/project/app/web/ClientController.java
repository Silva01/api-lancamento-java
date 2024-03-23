package silva.daniel.project.app.web;

import br.net.silva.business.value_object.output.NewAccountByNewClientResponseSuccess;
import br.net.silva.daniel.value_object.input.ActivateClient;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import br.net.silva.daniel.value_object.input.DeactivateClient;
import br.net.silva.daniel.value_object.input.EditClientInput;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silva.daniel.project.app.domain.client.ClientRequest;
import silva.daniel.project.app.domain.client.ClientService;
import silva.daniel.project.app.domain.client.EditClientRequest;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<NewAccountByNewClientResponseSuccess> createNewClient(@RequestBody @Valid ClientRequest newClient) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createNewClient(
                new ClientRequestDTO(
                    newClient.id(),
                    newClient.cpf(),
                    newClient.name(),
                    newClient.telephone(),
                    newClient.active(),
                    newClient.agency(),
                    newClient.addressRequestDTO()
                )));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateClient(@RequestBody @Valid EditClientRequest client) throws Exception {
        clientService.updateClient(new EditClientInput(
                client.cpf(),
                client.name(),
                client.telephone()));
    }

    @PostMapping("/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateClient(@RequestBody @Valid silva.daniel.project.app.domain.client.DeactivateClient request) throws Exception {
        clientService.deactivateClient(new DeactivateClient(request.cpf()));
    }

    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public void activateClient(@RequestBody @Valid silva.daniel.project.app.domain.client.ActivateClient request) throws Exception {
        clientService.activateClient(new ActivateClient(request.cpf()));
    }

}
