package silva.daniel.project.app.web.account;

import silva.daniel.project.app.commons.RequestAssertCommons;
import silva.daniel.project.app.commons.RequestBuilderCommons;

public class DeactivateCreditCardPrepare extends RequestAssertCommons implements RequestBuilderCommons {
    @Override
    public String url() {
        return "/credit-card/deactivate";
    }
}
