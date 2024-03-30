package silva.daniel.project.app.commons;

import org.springframework.http.HttpStatus;
import silva.daniel.project.app.domain.client.FailureResponse;

public class FailureMessageEnum {
    public static final FailureResponse INVALID_DATA_MESSAGE = new FailureResponse("Information is not valid", HttpStatus.NOT_ACCEPTABLE.value());
    public static final FailureResponse CLIENT_NOT_FOUND_MESSAGE = new FailureResponse("Client not exists in database", HttpStatus.NOT_FOUND.value());
    public static final FailureResponse CLIENT_ALREADY_DEACTIVATED = new FailureResponse("Client already deactivated", HttpStatus.NOT_ACCEPTABLE.value());
    public static final FailureResponse CLIENT_ALREADY_EXISTS = new FailureResponse("Client already exists in database", HttpStatus.CONFLICT.value());
    public static final FailureResponse ACCOUNT_NOT_FOUND_MESSAGE = new FailureResponse("Account not Found", HttpStatus.NOT_FOUND.value());
    public static final FailureResponse CREDIT_CARD_NOT_FOUND_MESSAGE = new FailureResponse("Credit card not exists", HttpStatus.NOT_FOUND.value());
    public static final FailureResponse CREDIT_CARD_ALREADY_DEACTIVATED_MESSAGE = new FailureResponse("Credit card already deactivated", HttpStatus.CONFLICT.value());
}
