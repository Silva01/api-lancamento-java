package silva.daniel.project.app.commons;

import silva.daniel.project.app.domain.account.request.DeactivateCreditCardRequest;

public interface RequestBuilderCommons {

    default DeactivateCreditCardRequest buildBaseRequest() {
        return new DeactivateCreditCardRequest(
                "12345678901",
                123456,
                1234,
                "1234567890123456");
    }
}
