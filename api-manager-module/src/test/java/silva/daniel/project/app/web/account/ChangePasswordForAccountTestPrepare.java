package silva.daniel.project.app.web.account;

import silva.daniel.project.app.commons.RequestAssertCommons;
import silva.daniel.project.app.commons.RequestBuilderCommons;
import silva.daniel.project.app.commons.ResponseBuilderCommons;

public class ChangePasswordForAccountTestPrepare extends RequestAssertCommons implements RequestBuilderCommons, ResponseBuilderCommons {
    @Override
    public String url() {
        return "/api/account/change/password";
    }
}
