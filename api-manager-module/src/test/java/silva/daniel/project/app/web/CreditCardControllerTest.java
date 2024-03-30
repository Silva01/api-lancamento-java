package silva.daniel.project.app.web;

import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.daniel.exception.ClientNotExistsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import silva.daniel.project.app.domain.account.request.DeactivateCreditCardRequest;
import silva.daniel.project.app.domain.account.service.CreditCardService;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.INVALID_DATA_MESSAGE;

@ActiveProfiles("unit")
@WebMvcTest(CreditCardController.class)
class CreditCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private CreditCardService service;

    @Test
    void deactivateCreditCard_WithValidData_ReturnsSuccess() throws Exception {
        final var request = new DeactivateCreditCardRequest("12345678901", 123456, 1234, "1234567890123456");
        mockMvc.perform(post("/credit-card/deactivate")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidDeactivateCreditCardRequests")
    @DisplayName("Deactivate credit card with invalid data returns exception")
    void deactivateCreditCard_WithInvalidData_ReturnsException(DeactivateCreditCardRequest request) throws Exception {
        var response = INVALID_DATA_MESSAGE.getResponse();
        mockMvc.perform(post("/credit-card/deactivate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value(response.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(response.getStatusCode()));
    }

    @Test
    void deactivateCreditCard_WithClientNotExists_ReturnsStatus406() throws Exception {
        doThrow(new ClientNotExistsException("Client not exists")).when(service).deactivateCreditCard(any(DeactivateCreditCardInput.class));
        final var request = new DeactivateCreditCardRequest("12345678901", 123456, 1234, "1234567890123456");
        var response = CLIENT_NOT_FOUND_MESSAGE.getResponse();

        mockMvc.perform(post("/credit-card/deactivate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(response.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(response.getStatusCode()));
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

}