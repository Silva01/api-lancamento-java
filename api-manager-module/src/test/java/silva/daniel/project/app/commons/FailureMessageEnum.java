package silva.daniel.project.app.commons;

import org.springframework.http.HttpStatus;
import silva.daniel.project.app.domain.client.FailureResponse;

public enum FailureMessageEnum {
    INVALID_DATA_MESSAGE(new FailureResponse("Information is not valid", HttpStatus.NOT_ACCEPTABLE.value())),
    CLIENT_NOT_FOUND_MESSAGE(new FailureResponse("Client not exists in database", HttpStatus.NOT_FOUND.value())),
    CLIENT_ALREADY_DEACTIVATED(new FailureResponse("Client already deactivated", HttpStatus.NOT_ACCEPTABLE.value())),
    ACCOUNT_NOT_FOUND_MESSAGE(new FailureResponse("Account not Found", HttpStatus.NOT_FOUND.value())),
    CREDIT_CARD_NOT_FOUND_MESSAGE(new FailureResponse("Credit card not exists", HttpStatus.NOT_FOUND.value()));

    private final FailureResponse response;

    FailureMessageEnum(FailureResponse response) {
        this.response = response;
    }

    public FailureResponse getResponse() {
        return response;
    }
}
