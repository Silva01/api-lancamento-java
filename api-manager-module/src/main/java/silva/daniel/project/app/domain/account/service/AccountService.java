package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.value_object.input.ActivateAccount;
import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.input.ChangePasswordDTO;
import br.net.silva.business.value_object.input.CreateNewAccountByCpfDTO;
import br.net.silva.business.value_object.input.DeactivateAccount;
import br.net.silva.business.value_object.input.GetInformationAccountInput;
import br.net.silva.business.value_object.output.AccountsByCpfResponseDto;
import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import br.net.silva.business.value_object.output.NewAccountResponse;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountFacade accountFacade;

    public AccountService(AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    public void editAgencyOfAccount(ChangeAgencyInput changeAgency) throws DomainGenericException {
        accountFacade.editAgencyOfAccount().exec(Source.of(changeAgency));
    }

    public GetInformationAccountOutput getAccountByCpf(final GetInformationAccountInput input) throws DomainGenericException {
        var source = new Source(new GetInformationAccountOutput(), input);
        accountFacade.getAccountByCpf().exec(source);
        return ((GetInformationAccountOutput) source.output());
    }

    public AccountsByCpfResponseDto getAllAccountsByCpf(GetInformationAccountInput getInformationAccountInput) throws DomainGenericException {
        var source = new Source(new AccountsByCpfResponseDto(), getInformationAccountInput);
        accountFacade.getAllAccount().exec(source);
        return ((AccountsByCpfResponseDto) source.output());
    }

    public void activateAccount(ActivateAccount input) throws DomainGenericException {
        accountFacade.activateAccount().exec(Source.of(input));
    }

    public void deactivateAccount(DeactivateAccount input) throws DomainGenericException {
        accountFacade.deactivateAccount().exec(Source.of(input));
    }

    public void changePassword(ChangePasswordDTO input) throws DomainGenericException {
        accountFacade.changePassword().exec(Source.of(input));
    }

    public NewAccountResponse createNewAccount(CreateNewAccountByCpfDTO input) throws DomainGenericException {
        final var source = new Source(new NewAccountResponse(), input);
        accountFacade.createNewAccount().exec(source);
        return ((NewAccountResponse) source.output());
    }
}
