package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import org.springframework.stereotype.Service;
import silva.daniel.project.app.service.FluxService;

@Service
public final class CreditCardService {

    private final FluxService fluxService;

    public CreditCardService(FluxService fluxService) {
        this.fluxService = fluxService;
    }

    public void deactivateCreditCard(final DeactivateCreditCardInput deactivateCreditCard) throws Exception {

    }
}
