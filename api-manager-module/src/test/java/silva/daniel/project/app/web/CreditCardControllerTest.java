package silva.daniel.project.app.web;

import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.daniel.exception.ClientNotExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import silva.daniel.project.app.commons.RequestAssertCommons;
import silva.daniel.project.app.domain.account.request.DeactivateCreditCardRequest;
import silva.daniel.project.app.domain.account.service.CreditCardService;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CREDIT_CARD_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.INVALID_DATA_MESSAGE;

@ActiveProfiles("unit")
class CreditCardControllerTest extends RequestAssertCommons {
    
    @MockBean
    private CreditCardService service;

    @Test
    void deactivateCreditCard_WithValidData_ReturnsSuccess() throws Exception {
        final var request = buildBaseRequest();
        successPostAssert(request, status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidDeactivateCreditCardRequests")
    @DisplayName("Deactivate credit card with invalid data returns exception")
    void deactivateCreditCard_WithInvalidData_ReturnsException(DeactivateCreditCardRequest request) throws Exception {
        var response = INVALID_DATA_MESSAGE.getResponse();
        failurePostAssert(request, response, status().isNotAcceptable());
    }

    @Test
    void deactivateCreditCard_WithClientNotExists_ReturnsStatus406() throws Exception {
        doThrow(new ClientNotExistsException("Client not exists")).when(service).deactivateCreditCard(any(DeactivateCreditCardInput.class));
        final var request = buildBaseRequest();
        var response = CLIENT_NOT_FOUND_MESSAGE.getResponse();
        failurePostAssert(request, response, status().isNotFound());
    }

    @Test
    void deactivateCreditCard_WithCreditCardNotFound_ReturnsStatus404() throws Exception {
        doThrow(new CreditCardNotExistsException("Credit card not exists")).when(service).deactivateCreditCard(any(DeactivateCreditCardInput.class));
        final var response = CREDIT_CARD_NOT_FOUND_MESSAGE.getResponse();
        final var request = buildBaseRequest();

        failurePostAssert(request, response, status().isNotFound());
    }

    private static Stream<Arguments> provideInvalidDeactivateCreditCardRequests() {
        return Stream.of(
                Arguments.of(new DeactivateCreditCardRequest(null, 123456, 1234, "1234567890123456")),
                Arguments.of(new DeactivateCreditCardRequest("12345678901", null, 1234, "1234567890123456")),
                Arguments.of(new DeactivateCreditCardRequest("12345678901", 123456, null, "1234567890123456")),
                Arguments.of(new DeactivateCreditCardRequest("12345678901", 123456, null, "")),
                Arguments.of(new DeactivateCreditCardRequest("12345678901", 123456, 1234, null)),
                Arguments.of(new DeactivateCreditCardRequest("", 123456, 1234, "1234567890123456")),
                Arguments.of(new DeactivateCreditCardRequest(null, 123456, 1234, "1234567890123456"))
        );
    }

    private DeactivateCreditCardRequest buildBaseRequest() {
        return new DeactivateCreditCardRequest(
                "12345678901",
                123456,
                1234,
                "1234567890123456");
    }

    @Override
    public String url() {
        return "/credit-card/deactivate";
    }
}