package silva.daniel.project.app.web;


import br.net.silva.business.exception.AccountAlreadyActiveException;
import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyException;
import br.net.silva.business.exception.AccountDeactivatedException;
import br.net.silva.business.exception.AccountExistsForCPFInformatedException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.exception.CreditCardAlreadyExistsException;
import br.net.silva.business.exception.CreditCardDeactivatedException;
import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.business.exception.ReversalTransactionNotFoundException;
import br.net.silva.business.exception.TransactionDuplicateException;
import br.net.silva.daniel.exception.ClientDeactivatedException;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.exceptions.ClientNotActiveException;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.exception.PasswordDivergentException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import silva.daniel.project.app.domain.client.FailureResponse;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ClientExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var failureResponse = new FailureResponse("Information is not valid", NOT_ACCEPTABLE.value());
        return super.handleExceptionInternal(ex, failureResponse, headers, NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<FailureResponse> handleGenericException(GenericException ex) {
        return createFailureMessage(ex.getMessage(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExistsClientRegistredException.class)
    public ResponseEntity<FailureResponse> handleExistsClientRegistredException(ExistsClientRegistredException ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(ClientNotExistsException.class)
    public ResponseEntity<FailureResponse> handleClientNotExistsException(ClientNotExistsException ex) {
        return createFailureMessage("Client not exists in database", NOT_FOUND);
    }

    @ExceptionHandler(CreditCardNotExistsException.class)
    public ResponseEntity<FailureResponse> handleCreditCardNotExistsException(CreditCardNotExistsException ex) {
        return createFailureMessage(ex.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(ClientNotActiveException.class)
    public ResponseEntity<FailureResponse> handleClientNotActiveException(ClientNotActiveException ex) {
        return createFailureMessage("Client already deactivated", CONFLICT);
    }

    @ExceptionHandler(CreditCardDeactivatedException.class)
    public ResponseEntity<FailureResponse> handleCreditCardDeactivatedException(CreditCardDeactivatedException ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(AccountNotExistsException.class)
    public ResponseEntity<FailureResponse> handleAccountNotExistsException(AccountNotExistsException ex) {
        return createFailureMessage("Account not Found", NOT_FOUND);
    }

    @ExceptionHandler(CreditCardAlreadyExistsException.class)
    public ResponseEntity<FailureResponse> handleCreditCardAlreadyExistsException(CreditCardAlreadyExistsException ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(AccountDeactivatedException.class)
    public ResponseEntity<FailureResponse> handleAccountDeactivatedException(AccountDeactivatedException ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(ClientDeactivatedException.class)
    public ResponseEntity<FailureResponse> handleAccountDeactivatedException(ClientDeactivatedException ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(AccountAlreadyExistsForNewAgencyException.class)
    public ResponseEntity<FailureResponse> handleAccountAlreadyExistsForNewAgencyException(AccountAlreadyExistsForNewAgencyException ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(AccountAlreadyActiveException.class)
    public ResponseEntity<FailureResponse> handleAccountAlreadyActiveException(AccountAlreadyActiveException ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(PasswordDivergentException.class)
    public ResponseEntity<FailureResponse> handlePasswordDivergentException(PasswordDivergentException ex) {
        return createFailureMessage(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(AccountExistsForCPFInformatedException.class)
    public ResponseEntity<FailureResponse> handleAccountExistsForCPFInformatedException(AccountExistsForCPFInformatedException ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(TransactionDuplicateException.class)
    public ResponseEntity<FailureResponse> handleTransactionDuplicateException(TransactionDuplicateException ex) {
        return createFailureMessage(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(ReversalTransactionNotFoundException.class)
    public ResponseEntity<FailureResponse> handleReversalTransactionNotFoundException(ReversalTransactionNotFoundException ex) {
        return createFailureMessage(ex.getMessage(), NOT_FOUND);
    }

    private static ResponseEntity<FailureResponse> createFailureMessage(String ex, HttpStatus statusCode) {
        var failureResponse = new FailureResponse(ex, statusCode.value());
        return ResponseEntity.status(statusCode).body(failureResponse);
    }
}
