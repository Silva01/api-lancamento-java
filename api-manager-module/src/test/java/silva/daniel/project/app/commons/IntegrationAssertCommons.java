package silva.daniel.project.app.commons;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import silva.daniel.project.app.domain.client.FailureResponse;

import static org.assertj.core.api.Assertions.assertThat;

public interface IntegrationAssertCommons {

    default void assertFailureApi(ResponseEntity<FailureResponse> sut, FailureResponse expected) {
        assertThat(sut.getStatusCode()).isEqualTo(HttpStatus.resolve(expected.getStatusCode()));
        assertThat(sut.getBody()).isNotNull();
        assertThat(sut.getBody().getMessage()).isEqualTo(expected.getMessage());
        assertThat(sut.getBody().getStatusCode()).isEqualTo(expected.getStatusCode());
    }
}
