package silva.daniel.project.app.commons;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import silva.daniel.project.app.domain.client.FailureResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_ALREADY_DEACTIVATED;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CREDIT_CARD_ALREADY_DEACTIVATED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CREDIT_CARD_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.INVALID_DATA_MESSAGE;

public interface IntegrationAssertCommons {

    default void assertInvalidData(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, INVALID_DATA_MESSAGE);
    }

    default void assertClientNotExists(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, CLIENT_NOT_FOUND_MESSAGE);
    }

    default void assertAccountNotExists(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, ACCOUNT_NOT_FOUND_MESSAGE);
    }

    default void assertClientAlreadyDeactivatedExists(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, CLIENT_ALREADY_DEACTIVATED);
    }

    default void assertAccountAlreadyWithNewAgencyNumber(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE);
    }

    default void assertCreditCardNotExists(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, CREDIT_CARD_NOT_FOUND_MESSAGE);
    }

    default void assertCredicCardAlreadyDeactivated(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, CREDIT_CARD_ALREADY_DEACTIVATED_MESSAGE);
    }

    default void assertFailureApi(ResponseEntity<FailureResponse> sut, FailureResponse expected) {
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.resolve(expected.getStatusCode()));
        assertThat(sut.getBody()).isNotNull();
        assertThat(sut.getBody().getMessage()).isEqualTo(expected.getMessage());
        assertThat(sut.getBody().getStatusCode()).isEqualTo(expected.getStatusCode());
    }
}
