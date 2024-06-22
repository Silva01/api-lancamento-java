package silva.daniel.project.app.web.client;

import silva.daniel.project.app.commons.RequestAssertCommons;
import silva.daniel.project.app.commons.RequestBuilderCommons;

public class AddressClientTestPrepare extends RequestAssertCommons implements RequestBuilderCommons {
    @Override
    public String url() {
        return "/clients/address";
    }
}
