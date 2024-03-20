package silva.daniel.project.app.web;

import br.net.silva.business.value_object.output.NewAccountByNewClientResponseSuccess;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import silva.daniel.project.app.domain.client.ClientRequest;
import silva.daniel.project.app.domain.client.ClientService;
import silva.daniel.project.app.domain.client.FailureResponse;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static silva.daniel.project.app.commons.ClientCommons.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNewClient_WithValidData_Returns201AndAccountData() throws Exception {
        final var mockResponse = mockResponse();
        when(service.createNewClient(any(ClientRequestDTO.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/clients")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestValidMock())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.agency").value(mockResponse.getAgency()))
                .andExpect(jsonPath("$.accountNumber").isNotEmpty())
                .andExpect(jsonPath("$.provisionalPassword").isNotEmpty());
    }

    @Test
    void createNewClient_WithClientExistInDatabase_Returns409() throws Exception {
        final var failureResponse = mockFailureResponse("Client already exists in database", 409);
        when(service.createNewClient(any(ClientRequestDTO.class))).thenThrow(new ExistsClientRegistredException(failureResponse.getMessage()));

        mockMvc.perform(post("/clients")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValidMock())))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(failureResponse.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(failureResponse.getStatusCode()));
    }

    @ParameterizedTest
    @MethodSource("provideRequestInvalidData")
    void createNewClient_WithInvalidData_Returns406(ClientRequest request) throws Exception {
        final var responseMock = mockFailureResponse("Information is not valid", 406);
        final var requestMockInvalid = requestInvalidAddressMock();

        mockMvc.perform(post("/clients")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotAcceptable())
                .andExpect(jsonPath("$.message").value(responseMock.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(responseMock.getStatusCode()));
    }

    @Test
    void editClient_WithValidData_Returns200() throws Exception {
        mockMvc.perform(put("/clients")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestValidMock())))
                .andExpect(status().isOk());
    }

    private NewAccountByNewClientResponseSuccess mockResponse() {
        final var response = new NewAccountByNewClientResponseSuccess();
        response.setAgency(1234);
        response.setAccountNumber(123456);
        response.setProvisionalPassword("test");
        return response;
    }

    private FailureResponse mockFailureResponse(String message, Integer statusCode) {
        return new FailureResponse(message, statusCode);
    }

    private static Stream<Arguments> provideRequestInvalidData() {
        return Stream.of(
                Arguments.of(requestInvalidAddressMock()),
                Arguments.of(requestCpfEmptyMock()),
                Arguments.of(requestNullCpfMock()),
                Arguments.of(requestInvalidAgencyMock()),
                Arguments.of(requestInvalidNegativeAgencyMock()),
                Arguments.of(requestInvalidZeroAgencyMock())
        );
    }
}