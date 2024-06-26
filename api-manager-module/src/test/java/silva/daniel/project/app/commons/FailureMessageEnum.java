package silva.daniel.project.app.commons;

import org.springframework.http.HttpStatus;
import silva.daniel.project.app.domain.client.FailureResponse;

public class FailureMessageEnum {
    public static final FailureResponse INVALID_DATA_MESSAGE = new FailureResponse("Information is not valid", HttpStatus.NOT_ACCEPTABLE.value());
    public static final FailureResponse CLIENT_NOT_FOUND_MESSAGE = new FailureResponse("Client not exists in database", HttpStatus.NOT_FOUND.value());
    public static final FailureResponse CLIENT_ALREADY_DEACTIVATED = new FailureResponse("Client already deactivated", HttpStatus.CONFLICT.value());
    public static final FailureResponse CLIENT_DEACTIVATED = new FailureResponse("Client is Deactivated", HttpStatus.CONFLICT.value());
    public static final FailureResponse CLIENT_ALREADY_EXISTS = new FailureResponse("Client already exists in database", HttpStatus.CONFLICT.value());
    public static final FailureResponse CLIENT_ALREADY_ACTIVATED = new FailureResponse("Client already activated", HttpStatus.CONFLICT.value());
    public static final FailureResponse CLIENT_ALREADY_ACCOUNT_ACTIVE = new FailureResponse("Account already exists for the CPF informed", HttpStatus.CONFLICT.value());
    public static final FailureResponse ACCOUNT_NOT_FOUND_MESSAGE = new FailureResponse("Account not Found", HttpStatus.NOT_FOUND.value());
    public static final FailureResponse ACCOUNT_ALREADY_WITH_NEW_AGENCY_NUMBER_MESSAGE = new FailureResponse("Account With new agency already exists", HttpStatus.CONFLICT.value());
    public static final FailureResponse ACCOUNT_ALREADY_DEACTIVATED_MESSAGE = new FailureResponse("Account is Deactivated", HttpStatus.CONFLICT.value());
    public static final FailureResponse ACCOUNT_ALREADY_ACTIVATED_MESSAGE = new FailureResponse("Account already active", HttpStatus.CONFLICT.value());
    public static final FailureResponse ACCOUNT_WITH_PASSWORD_DIFFERENT = new FailureResponse("Password is different", HttpStatus.BAD_REQUEST.value());
    public static final FailureResponse CREDIT_CARD_NOT_FOUND_MESSAGE = new FailureResponse("Credit card not exists in the account", HttpStatus.NOT_FOUND.value());
    public static final FailureResponse CREDIT_CARD_ALREADY_DEACTIVATED_MESSAGE = new FailureResponse("Credit card deactivated in the account", HttpStatus.CONFLICT.value());
    public static final FailureResponse CREDIT_CARD_ALREADY_EXISTS_MESSAGE = new FailureResponse("Credit card already exists", HttpStatus.CONFLICT.value());
    public static final FailureResponse TRANSACTION_DUPLICATE_MESSAGE = new FailureResponse("Transaction has 2 or more equals transactions.", HttpStatus.BAD_REQUEST.value());
    public static final FailureResponse TRANSACTION_NOT_FOUND_MESSAGE = new FailureResponse("Transaction not found", HttpStatus.NOT_FOUND.value());
    public static final FailureResponse TRANSACTION_ALREADY_REFUNDED_MESSAGE = new FailureResponse("Transaction already refunded", HttpStatus.CONFLICT.value());


    public static final FailureResponse QUEUE_ERROR_MESSAGE = new FailureResponse("Error when saving message in queue", HttpStatus.INTERNAL_SERVER_ERROR.value());
}
