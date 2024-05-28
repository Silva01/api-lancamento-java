package silva.daniel.project.app.commons;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import silva.daniel.project.app.domain.client.FailureResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_ACTIVATED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_DEACTIVATED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_WITH_PASSWORD_DIFFERENT;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_ALREADY_ACCOUNT_ACTIVE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_ALREADY_ACTIVATED;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_ALREADY_DEACTIVATED;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_ALREADY_EXISTS;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_DEACTIVATED;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CREDIT_CARD_ALREADY_DEACTIVATED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CREDIT_CARD_ALREADY_EXISTS_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CREDIT_CARD_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.INVALID_DATA_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.TRANSACTION_DUPLICATE_MESSAGE;

public interface IntegrationAssertCommons {

    default void assertInvalidData(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, INVALID_DATA_MESSAGE);
    }

    default void assertClientNotExists(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, CLIENT_NOT_FOUND_MESSAGE);
    }

    default void assertClientAlreadyExists(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, CLIENT_ALREADY_EXISTS);
    }

    default void assertClientAlreadyDeactivatedExists(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, CLIENT_ALREADY_DEACTIVATED);
    }

    default void assertClientDeactivatedExists(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, CLIENT_DEACTIVATED);
    }

    default void assertClientActivated(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, CLIENT_ALREADY_ACTIVATED);
    }

    default void assertClientAlreadyAccountActive(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, CLIENT_ALREADY_ACCOUNT_ACTIVE);
    }

    default void assertAccountNotExists(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, ACCOUNT_NOT_FOUND_MESSAGE);
    }

    default void assertAccountAlreadyWithNewAgencyNumber(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE);
    }

    default void assertAccountAlreadyDeactivated(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, ACCOUNT_ALREADY_DEACTIVATED_MESSAGE);
    }

    default void assertAccountWithPasswordDifferent(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, ACCOUNT_WITH_PASSWORD_DIFFERENT);
    }

    default void assertAccountAlreadyActive(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, ACCOUNT_ALREADY_ACTIVATED_MESSAGE);
    }

    default void assertCreditCardNotExists(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, CREDIT_CARD_NOT_FOUND_MESSAGE);
    }

    default void assertCredicCardAlreadyDeactivated(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, CREDIT_CARD_ALREADY_DEACTIVATED_MESSAGE);
    }

    default void assertCredicCardAlreadyExists(ResponseEntity<FailureResponse> sut) {
        assertFailureApi(sut, CREDIT_CARD_ALREADY_EXISTS_MESSAGE);
    }

    default void assertDuplicateTransaction(ResponseEntity<FailureResponse> sut){
        assertFailureApi(sut, TRANSACTION_DUPLICATE_MESSAGE);
    }

    default void assertFailureApi(ResponseEntity<FailureResponse> sut, FailureResponse expected) {
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.resolve(expected.getStatusCode()));
        assertThat(sut.getBody()).isNotNull();
        assertThat(sut.getBody().getMessage()).isEqualTo(expected.getMessage());
        assertThat(sut.getBody().getStatusCode()).isEqualTo(expected.getStatusCode());
    }

}
