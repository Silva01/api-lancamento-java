package silva.daniel.project.app.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import silva.daniel.project.app.domain.account.request.DeactivateCreditCardRequest;

@RestController
@RequestMapping("/credit-card")
public class CreditCardController {

    @PostMapping("/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateCreditCard(@Valid @RequestBody DeactivateCreditCardRequest request) {
    }

}
