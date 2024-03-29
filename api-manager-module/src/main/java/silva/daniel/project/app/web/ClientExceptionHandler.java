package silva.daniel.project.app.web;


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

import static org.springframework.http.HttpStatus.*;

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

    @ExceptionHandler(ClientNotActiveException.class)
    public ResponseEntity<FailureResponse> handleClientNotExistsException(ClientNotActiveException ex) {
        var failureResponse = new FailureResponse("Client already deactivated", CONFLICT.value());
        return ResponseEntity.status(CONFLICT).body(failureResponse);
    }
}
