package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.daniel.shared.application.value_object.Source;
import org.springframework.stereotype.Service;

@Service
public final class CreditCardService {

    private final CreditCardFacade creditCardFacade;

    public CreditCardService(CreditCardFacade creditCardFacade) {
        this.creditCardFacade = creditCardFacade;
    }

    public void deactivateCreditCard(final DeactivateCreditCardInput deactivateCreditCard) throws Exception {
        creditCardFacade.deactivateCreditCard().exec(Source.of(deactivateCreditCard));
    }

    public void createCreditCard(final CreateCreditCardInput input) throws Exception {
        creditCardFacade.createCreditCard().exec(Source.of(input));
    }
}
