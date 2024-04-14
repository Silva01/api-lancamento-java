package silva.daniel.project.app.web;

import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.input.GetInformationAccountInput;
import br.net.silva.business.value_object.output.AccountsByCpfResponseDto;
import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import silva.daniel.project.app.domain.account.request.EditAgencyOfAccountRequest;
import silva.daniel.project.app.domain.account.service.AccountService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping("/update/agency")
    @ResponseStatus(HttpStatus.OK)
    public void updateAgencyOfAccount(@Valid @RequestBody EditAgencyOfAccountRequest request) throws Exception {
        accountService.editAgencyOfAccount(new ChangeAgencyInput(
                request.cpf(), request.accountNumber(), request.agencyNumber(), request.newAgencyNumber())
        );
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<GetInformationAccountOutput> getAccountByCpf(@PathVariable String cpf) throws Exception {
        return ResponseEntity.ok(accountService.getAccountByCpf(new GetInformationAccountInput(cpf)));
    }

    @GetMapping("/all/{cpf}")
    public ResponseEntity<AccountsByCpfResponseDto> getAllAccountsByCpf(@PathVariable String cpf) throws Exception {
        return ResponseEntity.ok(accountService.getAllAccountsByCpf(new GetInformationAccountInput(cpf)));
    }
}
