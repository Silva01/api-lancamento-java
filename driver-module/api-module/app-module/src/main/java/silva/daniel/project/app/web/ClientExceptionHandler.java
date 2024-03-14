package silva.daniel.project.app.web;


import br.net.silva.daniel.exception.ExistsClientRegistredException;
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

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

@RestControllerAdvice
public class ClientExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var failureResponse = new FailureResponse("Information is not valid", NOT_ACCEPTABLE.value());
        return super.handleExceptionInternal(ex, failureResponse, headers, NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(ExistsClientRegistredException.class)
    public ResponseEntity<FailureResponse> handleExistsClientRegistredException(ExistsClientRegistredException ex) {
        var failureResponse = new FailureResponse("Client already exists in database", CONFLICT.value());
        return ResponseEntity.status(CONFLICT).body(failureResponse);
    }
}
