package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.input.GetInformationAccountInput;
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

    public void getAccountByCpf(final GetInformationAccountInput input) throws Exception {
        //TODO: Will be implemented
    }
}
