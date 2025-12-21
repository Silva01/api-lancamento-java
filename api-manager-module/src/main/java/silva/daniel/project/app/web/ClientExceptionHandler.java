package silva.daniel.project.app.web;


import br.net.silva.business.exception.AccountAlreadyActiveExceptionDomain;
import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyExceptionDomain;
import br.net.silva.business.exception.AccountDeactivatedExceptionDomain;
import br.net.silva.business.exception.AccountExistsForCPFInformatedExceptionDomain;
import br.net.silva.business.exception.AccountNotExistsExceptionDomain;
import br.net.silva.business.exception.CreditCardAlreadyExistsExceptionDomain;
import br.net.silva.business.exception.CreditCardDeactivatedExceptionDomain;
import br.net.silva.business.exception.CreditCardNotExistsExceptionDomain;
import br.net.silva.business.exception.ReversalTransactionAlreadyRefundedExceptionDomain;
import br.net.silva.business.exception.TransactionDuplicateExceptionDomain;
import br.net.silva.business.exception.TransactionNotExistsExceptionDomain;
import br.net.silva.daniel.exception.ClientDeactivatedExceptionDomain;
import br.net.silva.daniel.exception.ClientNotExistsExceptionDomain;
import br.net.silva.daniel.exception.ExistsClientRegistredExceptionDomain;
import br.net.silva.daniel.exceptions.ClientNotActiveException;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;
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

    @ExceptionHandler(DomainGenericException.class)
    public ResponseEntity<FailureResponse> handleGenericException(DomainGenericException ex) {
        return createFailureMessage(ex.getMessage(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExistsClientRegistredExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleExistsClientRegistredException(ExistsClientRegistredExceptionDomain ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(ClientNotExistsExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleClientNotExistsException(ClientNotExistsExceptionDomain ex) {
        return createFailureMessage("Client not exists in database", NOT_FOUND);
    }

    @ExceptionHandler(CreditCardNotExistsExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleCreditCardNotExistsException(CreditCardNotExistsExceptionDomain ex) {
        return createFailureMessage(ex.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(ClientNotActiveException.class)
    public ResponseEntity<FailureResponse> handleClientNotActiveException(ClientNotActiveException ex) {
        return createFailureMessage("Client already deactivated", CONFLICT);
    }

    @ExceptionHandler(CreditCardDeactivatedExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleCreditCardDeactivatedException(CreditCardDeactivatedExceptionDomain ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(AccountNotExistsExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleAccountNotExistsException(AccountNotExistsExceptionDomain ex) {
        return createFailureMessage("Account not Found", NOT_FOUND);
    }

    @ExceptionHandler(CreditCardAlreadyExistsExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleCreditCardAlreadyExistsException(CreditCardAlreadyExistsExceptionDomain ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(AccountDeactivatedExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleAccountDeactivatedException(AccountDeactivatedExceptionDomain ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(ClientDeactivatedExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleAccountDeactivatedException(ClientDeactivatedExceptionDomain ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(AccountAlreadyExistsForNewAgencyExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleAccountAlreadyExistsForNewAgencyException(AccountAlreadyExistsForNewAgencyExceptionDomain ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(AccountAlreadyActiveExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleAccountAlreadyActiveException(AccountAlreadyActiveExceptionDomain ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(PasswordDivergentException.class)
    public ResponseEntity<FailureResponse> handlePasswordDivergentException(PasswordDivergentException ex) {
        return createFailureMessage(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(AccountExistsForCPFInformatedExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleAccountExistsForCPFInformatedException(AccountExistsForCPFInformatedExceptionDomain ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    @ExceptionHandler(TransactionDuplicateExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleTransactionDuplicateException(TransactionDuplicateExceptionDomain ex) {
        return createFailureMessage(ex.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(TransactionNotExistsExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleReversalTransactionNotFoundException(TransactionNotExistsExceptionDomain ex) {
        return createFailureMessage(ex.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(ReversalTransactionAlreadyRefundedExceptionDomain.class)
    public ResponseEntity<FailureResponse> handleReversalTransactionAlreadyRefundedException(ReversalTransactionAlreadyRefundedExceptionDomain ex) {
        return createFailureMessage(ex.getMessage(), CONFLICT);
    }

    private static ResponseEntity<FailureResponse> createFailureMessage(String ex, HttpStatus statusCode) {
        var failureResponse = new FailureResponse(ex, statusCode.value());
        return ResponseEntity.status(statusCode).body(failureResponse);
    }
}
