package silva.daniel.project.app.web;

import br.net.silva.business.value_object.output.NewAccountByNewClientResponseSuccess;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import silva.daniel.project.app.domain.client.ClientRequest;
import silva.daniel.project.app.domain.client.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<NewAccountByNewClientResponseSuccess> createNewClient(@RequestBody @Valid ClientRequest newClient) {
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

}
