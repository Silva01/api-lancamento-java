package silva.daniel.project.app.commons;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public final class RequestIntegrationCommons {

    private final TestRestTemplate restTemplate;

    public RequestIntegrationCommons(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    public <T> void assertPutRequest(String url, Object pRequest, Class<?> response, Consumer<ResponseEntity<T>> assertFunction) {
        assertFunction.accept((ResponseEntity<T>) request(url, HttpMethod.PUT, pRequest, response));
    }

    public void assertPostRequest(String url, Object pRequest, Class<?> response, Consumer<ResponseEntity<?>> assertFunction) {
        assertFunction.accept(request(url, HttpMethod.POST, pRequest, response));
    }

    private ResponseEntity<?> request(String url, HttpMethod method, Object request, Class<?> response) {
        var httpEntity = new HttpEntity<>(request);
        return restTemplate.exchange(url, method, httpEntity, response);
    }
}
