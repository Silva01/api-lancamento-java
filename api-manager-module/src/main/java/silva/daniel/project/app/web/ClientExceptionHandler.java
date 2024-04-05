package silva.daniel.project.app.web;


import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyException;
import br.net.silva.business.exception.AccountDeactivatedException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.exception.CreditCardAlreadyExistsException;
import br.net.silva.business.exception.CreditCardDeactivatedException;
import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.daniel.exception.ClientDeactivatedException;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.exceptions.ClientNotActiveException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import silva.daniel.project.app.domain.client.FailureResponse;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ClientExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var failureResponse = new FailureResponse("Information is not valid", NOT_ACCEPTABLE.value());
        return super.handleExceptionInternal(ex, failureResponse, headers, NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(ExistsClientRegistredException.class)
    public ResponseEntity<FailureResponse> handleExistsClientRegistredException(ExistsClientRegistredException ex) {
        var failureResponse = new FailureResponse(ex.getMessage(), CONFLICT.value());
        return ResponseEntity.status(CONFLICT).body(failureResponse);
    }

    @ExceptionHandler(ClientNotExistsException.class)
    public ResponseEntity<FailureResponse> handleClientNotExistsException(ClientNotExistsException ex) {
        var failureResponse = new FailureResponse("Client not exists in database", NOT_FOUND.value());
        return ResponseEntity.status(NOT_FOUND).body(failureResponse);
    }

    @ExceptionHandler(CreditCardNotExistsException.class)
    public ResponseEntity<FailureResponse> handleCreditCardNotExistsException(CreditCardNotExistsException ex) {
        var failureResponse = new FailureResponse(ex.getMessage(), NOT_FOUND.value());
        return ResponseEntity.status(NOT_FOUND).body(failureResponse);
    }

    @ExceptionHandler(ClientNotActiveException.class)
    public ResponseEntity<FailureResponse> handleClientNotActiveException(ClientNotActiveException ex) {
        var failureResponse = new FailureResponse("Client already deactivated", CONFLICT.value());
        return ResponseEntity.status(CONFLICT).body(failureResponse);
    }

    @ExceptionHandler(CreditCardDeactivatedException.class)
    public ResponseEntity<FailureResponse> handleCreditCardDeactivatedException(CreditCardDeactivatedException ex) {
        var failureResponse = new FailureResponse(ex.getMessage(), CONFLICT.value());
        return ResponseEntity.status(CONFLICT).body(failureResponse);
    }

    @ExceptionHandler(AccountNotExistsException.class)
    public ResponseEntity<FailureResponse> handleAccountNotExistsException(AccountNotExistsException ex) {
        var failureResponse = new FailureResponse("Account not Found", NOT_FOUND.value());
        return ResponseEntity.status(NOT_FOUND).body(failureResponse);
    }

    @ExceptionHandler(CreditCardAlreadyExistsException.class)
    public ResponseEntity<FailureResponse> handleCreditCardAlreadyExistsException(CreditCardAlreadyExistsException ex) {
        var failureResponse = new FailureResponse(ex.getMessage(), CONFLICT.value());
        return ResponseEntity.status(CONFLICT).body(failureResponse);
    }

    @ExceptionHandler(AccountDeactivatedException.class)
    public ResponseEntity<FailureResponse> handleAccountDeactivatedException(AccountDeactivatedException ex) {
        var failureResponse = new FailureResponse(ex.getMessage(), CONFLICT.value());
        return ResponseEntity.status(CONFLICT).body(failureResponse);
    }

    @ExceptionHandler(ClientDeactivatedException.class)
    public ResponseEntity<FailureResponse> handleAccountDeactivatedException(ClientDeactivatedException ex) {
        var failureResponse = new FailureResponse(ex.getMessage(), CONFLICT.value());
        return ResponseEntity.status(CONFLICT).body(failureResponse);
    }

    @ExceptionHandler(AccountAlreadyExistsForNewAgencyException.class)
    public ResponseEntity<FailureResponse> handleAccountAlreadyExistsForNewAgencyException(AccountAlreadyExistsForNewAgencyException ex) {
        var failureResponse = new FailureResponse(ex.getMessage(), CONFLICT.value());
        return ResponseEntity.status(CONFLICT).body(failureResponse);
    }
}
