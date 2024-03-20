package silva.daniel.project.app.domain.client;

import silva.daniel.project.app.template.AbstractErrorResponse;

public class FailureResponse extends AbstractErrorResponse {
    public FailureResponse(String message, int statusCode) {
        super(message, statusCode);
    }
}
