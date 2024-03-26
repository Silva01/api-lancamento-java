package silva.daniel.project.app.web;

import br.net.silva.business.value_object.output.NewAccountByNewClientResponseSuccess;
import br.net.silva.daniel.value_object.input.ActivateClient;
import br.net.silva.daniel.value_object.input.AddressRequestDTO;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import br.net.silva.daniel.value_object.input.DeactivateClient;
import br.net.silva.daniel.value_object.input.EditClientInput;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import silva.daniel.project.app.domain.client.request.AddressRequest;
import silva.daniel.project.app.domain.client.request.ClientRequest;
import silva.daniel.project.app.domain.client.request.EditClientRequest;
import silva.daniel.project.app.domain.client.service.ClientService;

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
    public void deactivateClient(@RequestBody @Valid silva.daniel.project.app.domain.client.request.DeactivateClient request) throws Exception {
        clientService.deactivateClient(new DeactivateClient(request.cpf()));
    }

    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public void activateClient(@RequestBody @Valid silva.daniel.project.app.domain.client.request.ActivateClient request) throws Exception {
        clientService.activateClient(new ActivateClient(request.cpf()));
    }

    @PutMapping("/address")
    @ResponseStatus(HttpStatus.OK)
    public void updateAddress(@RequestBody @Valid AddressRequest request) throws Exception {
        clientService.updateAddress(request.cpf(), new AddressRequestDTO(
                request.street(),
                request.number(),
                request.complement(),
                request.neighborhood(),
                request.state(),
                request.city(),
                request.zipCode()));
    }

}
