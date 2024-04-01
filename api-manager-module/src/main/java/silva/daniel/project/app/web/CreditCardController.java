package silva.daniel.project.app.web;

import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import silva.daniel.project.app.domain.account.request.CreateCreditCardRequest;
import silva.daniel.project.app.domain.account.request.DeactivateCreditCardRequest;
import silva.daniel.project.app.domain.account.service.CreditCardService;

@RestController
@RequestMapping("/credit-card")
public final class CreditCardController {

    private final CreditCardService service;

    public CreditCardController(CreditCardService service) {
        this.service = service;
    }

    @PostMapping("/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateCreditCard(@Valid @RequestBody DeactivateCreditCardRequest request) throws Exception {
        var input = new DeactivateCreditCardInput(request.cpf(), request.accountNumber(), request.agency(), request.creditCardNumber());
        service.deactivateCreditCard(input);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCreditCard(@Valid @RequestBody CreateCreditCardRequest request) throws Exception {
        var input = new CreateCreditCardInput(request.cpf(), request.account(), request.agencyNumber());
//        service.createCreditCard(input);
    }

}
