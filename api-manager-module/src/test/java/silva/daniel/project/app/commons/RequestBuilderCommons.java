package silva.daniel.project.app.commons;

import br.net.silva.business.value_object.input.ActivateAccount;
import br.net.silva.business.value_object.input.DeactivateAccount;
import silva.daniel.project.app.domain.account.request.ActivateAccountRequest;
import silva.daniel.project.app.domain.account.request.CreateCreditCardRequest;
import silva.daniel.project.app.domain.account.request.DeactivateCreditCardRequest;
import silva.daniel.project.app.domain.account.request.EditAgencyOfAccountRequest;

public interface RequestBuilderCommons {

    default DeactivateCreditCardRequest buildBaseRequest() {
        return new DeactivateCreditCardRequest(
                "12345678901",
                123456,
                1234,
                "1234567890123456");
    }

    default CreateCreditCardRequest buildBaseCreateCreditCardRequest() {
        return new CreateCreditCardRequest("12345678901", 123456, 1234);
    }

    default EditAgencyOfAccountRequest buildBaseEditAgencyOfAccountRequest() {
        return new EditAgencyOfAccountRequest("12345678901", 123456, 1234, 3333);
    }

    default ActivateAccountRequest buildBaseActivateAccount() {
        return new ActivateAccountRequest("12345678901", 1, 1234);
    }

    default DeactivateAccount buildBaseDeactivateAccount() {
        return new DeactivateAccount("12345678901", 1, 1234);
    }
}
