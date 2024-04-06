package silva.daniel.project.app.web.client;

import silva.daniel.project.app.commons.RequestAssertCommons;
import silva.daniel.project.app.commons.RequestBuilderCommons;
import silva.daniel.project.app.commons.ResponseBuilderCommons;

public class GetInformationAccountTestPrepare extends RequestAssertCommons implements RequestBuilderCommons, ResponseBuilderCommons {
    @Override
    public String url() {
        return "/api/account/{cpf}";
    }
}
