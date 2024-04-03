package silva.daniel.project.app.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import silva.daniel.project.app.domain.account.request.EditAgencyOfAccountRequest;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @PutMapping("/update/agency")
    @ResponseStatus(HttpStatus.OK)
    public void updateAgencyOfAccount(@Valid @RequestBody EditAgencyOfAccountRequest request) {

    }
}
