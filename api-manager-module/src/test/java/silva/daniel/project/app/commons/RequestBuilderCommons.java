package silva.daniel.project.app.commons;

import silva.daniel.project.app.domain.account.request.ActivateAccountRequest;
import silva.daniel.project.app.domain.account.request.CreateCreditCardRequest;
import silva.daniel.project.app.domain.account.request.DeactivateAccountRequest;
import silva.daniel.project.app.domain.account.request.DeactivateCreditCardRequest;
import silva.daniel.project.app.domain.account.request.EditAgencyOfAccountRequest;
import silva.daniel.project.app.domain.account.request.ChangePasswordRequest;

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

    default DeactivateAccountRequest buildBaseDeactivateAccount() {
        return new DeactivateAccountRequest("12345678901", 123456, 1234);
    }

    default ChangePasswordRequest buildBaseCreateNewPasswordForAccount() {
        return ChangePasswordRequest
                .builder()
                .agency(1)
                .accountNumber(1234)
                .cpf("12345678901")
                .password("123456")
                .newPassword("876543")
                .build();
    }
}
