package silva.daniel.project.app.web.account.transaction;

import silva.daniel.project.app.commons.RequestAssertCommons;
import silva.daniel.project.app.commons.RequestBuilderCommons;
import silva.daniel.project.app.commons.ResponseBuilderCommons;

public class RefundTransactionTestPrepare extends RequestAssertCommons implements RequestBuilderCommons, ResponseBuilderCommons {
    @Override
    public String url() {
        return "/api/transaction/refund";
    }
}
