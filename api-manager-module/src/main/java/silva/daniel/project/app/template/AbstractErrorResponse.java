package silva.daniel.project.app.template;

import lombok.Getter;

@Getter
public abstract class AbstractErrorResponse {

    private final String message;
    private final int statusCode;

    protected AbstractErrorResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
