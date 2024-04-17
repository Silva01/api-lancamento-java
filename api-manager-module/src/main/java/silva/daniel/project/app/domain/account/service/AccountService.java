package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.value_object.input.ActivateAccount;
import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.input.DeactivateAccount;
import br.net.silva.business.value_object.input.GetInformationAccountInput;
import br.net.silva.business.value_object.output.AccountsByCpfResponseDto;
import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import br.net.silva.daniel.shared.application.value_object.Source;
import org.springframework.stereotype.Service;
import silva.daniel.project.app.service.FluxService;

@Service
public class AccountService {

    private final FluxService fluxService;

    public AccountService(FluxService fluxService) {
        this.fluxService = fluxService;
    }

    public void editAgencyOfAccount(ChangeAgencyInput changeAgency) throws Exception {
        fluxService.fluxEditAgencyOfAccount().exec(Source.of(changeAgency));
    }

    public GetInformationAccountOutput getAccountByCpf(final GetInformationAccountInput input) throws Exception {
        var source = new Source(new GetInformationAccountOutput(), input);
        fluxService.fluxGetAccountByCpf().exec(source);
        return ((GetInformationAccountOutput) source.output());
    }

    public AccountsByCpfResponseDto getAllAccountsByCpf(GetInformationAccountInput getInformationAccountInput) throws Exception {
        var source = new Source(new AccountsByCpfResponseDto(), getInformationAccountInput);
        fluxService.fluxGetAllAccount().exec(source);
        return ((AccountsByCpfResponseDto) source.output());
    }

    public void activateAccount(ActivateAccount input) throws Exception {
        fluxService.fluxActivateAccount().exec(Source.of(input));
    }

    public void deactivateAccount(DeactivateAccount input) throws Exception {
        fluxService.fluxDeactivateAccount().exec(Source.of(input));
    }
}
