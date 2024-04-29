package silva.daniel.project.app.web;

import br.net.silva.business.value_object.input.ActivateAccount;
import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.input.ChangePasswordDTO;
import br.net.silva.business.value_object.input.CreateNewAccountByCpfDTO;
import br.net.silva.business.value_object.input.DeactivateAccount;
import br.net.silva.business.value_object.input.GetInformationAccountInput;
import br.net.silva.business.value_object.output.AccountsByCpfResponseDto;
import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import br.net.silva.business.value_object.output.NewAccountResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import silva.daniel.project.app.domain.account.request.ActivateAccountRequest;
import silva.daniel.project.app.domain.account.request.ChangePasswordRequest;
import silva.daniel.project.app.domain.account.request.DeactivateAccountRequest;
import silva.daniel.project.app.domain.account.request.EditAgencyOfAccountRequest;
import silva.daniel.project.app.domain.account.request.NewAccountRequest;
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

    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public void activateAccount(@Valid @RequestBody ActivateAccountRequest request) throws Exception {
        accountService.activateAccount(new ActivateAccount(request.agency(), request.accountNumber(), request.cpf()));
    }

    @PostMapping("/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateAccount(@Valid @RequestBody DeactivateAccountRequest request) throws Exception {
        accountService.deactivateAccount(new DeactivateAccount(request.getCpf(), request.getAgency(), request.getAccount()));
    }

    @PutMapping("/change/password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@Valid @RequestBody ChangePasswordRequest request) throws Exception {
        accountService.changePassword(new ChangePasswordDTO(
                request.getCpf(),
                request.getAgency(),
                request.getAccountNumber(),
                request.getPassword(),
                request.getNewPassword()
        ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<NewAccountResponse> createAccount(@Valid @RequestBody NewAccountRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createNewAccount(new CreateNewAccountByCpfDTO(
                request.cpf(),
                request.agencyNumber(),
                request.password()
        )));
    }
}
