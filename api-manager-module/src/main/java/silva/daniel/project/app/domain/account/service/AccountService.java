package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.input.GetInformationAccountInput;
import br.net.silva.business.value_object.output.AccountsByCpfResponseDto;
import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import br.net.silva.daniel.shared.application.value_object.Source;
import org.springframework.stereotype.Service;
import silva.daniel.project.app.service.FluxService;

import java.util.Collections;
import java.util.List;

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

    public AccountsByCpfResponseDto getAllAccountsByCpf(String cpf) throws Exception {
        return null;
    }
}
