package silva.daniel.project.app.web.account;

import silva.daniel.project.app.commons.RequestAssertCommons;

public class EditAgencyOfAccountPrepare extends RequestAssertCommons {
    @Override
    public String url() {
        return "/api/account/update/agency";
    }
}
