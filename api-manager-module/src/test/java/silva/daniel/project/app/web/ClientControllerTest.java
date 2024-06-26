package silva.daniel.project.app.web;

import br.net.silva.business.value_object.output.NewAccountByNewClientResponseSuccess;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.exceptions.ClientNotActiveException;
import br.net.silva.daniel.value_object.input.ClientRequestDTO;
import br.net.silva.daniel.value_object.input.DeactivateClient;
import br.net.silva.daniel.value_object.input.EditAddressInput;
import br.net.silva.daniel.value_object.input.EditClientInput;
import br.net.silva.daniel.value_object.output.GetInformationClientResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import silva.daniel.project.app.domain.client.FailureResponse;
import silva.daniel.project.app.domain.client.request.ActivateClient;
import silva.daniel.project.app.domain.client.request.AddressRequest;
import silva.daniel.project.app.domain.client.request.ClientRequest;
import silva.daniel.project.app.domain.client.request.EditStatusClientRequest;
import silva.daniel.project.app.domain.client.service.ClientService;
import silva.daniel.project.app.web.client.ActivateClientTestPrepare;
import silva.daniel.project.app.web.client.AddressClientTestPrepare;
import silva.daniel.project.app.web.client.CreateClientTestPrepare;
import silva.daniel.project.app.web.client.DeactivateClientTestPrepare;
import silva.daniel.project.app.web.client.GetClientTestPrepare;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static silva.daniel.project.app.commons.ClientCommons.activateClientMock;
import static silva.daniel.project.app.commons.ClientCommons.editClientRequestMock;
import static silva.daniel.project.app.commons.ClientCommons.editStatusClientRequestMock;
import static silva.daniel.project.app.commons.ClientCommons.requestCpfEmptyMock;
import static silva.daniel.project.app.commons.ClientCommons.requestInvalidAddressMock;
import static silva.daniel.project.app.commons.ClientCommons.requestInvalidAgencyMock;
import static silva.daniel.project.app.commons.ClientCommons.requestInvalidNegativeAgencyMock;
import static silva.daniel.project.app.commons.ClientCommons.requestInvalidZeroAgencyMock;
import static silva.daniel.project.app.commons.ClientCommons.requestNullCpfMock;
import static silva.daniel.project.app.commons.ClientCommons.requestValidMock;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_ALREADY_DEACTIVATED;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_ALREADY_EXISTS;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.INVALID_DATA_MESSAGE;

@ActiveProfiles("unit")
@WebMvcTest(ClientController.class)
@Import({CreateClientTestPrepare.class, DeactivateClientTestPrepare.class, ActivateClientTestPrepare.class, AddressClientTestPrepare.class, GetClientTestPrepare.class}) //TODO: Aqui acho interessante criar uma anotação propria pois vai importar mais de um prepare
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CreateClientTestPrepare prepareAsserts;

    @Autowired
    private DeactivateClientTestPrepare deactivateRequestPrepare;

    @Autowired
    private ActivateClientTestPrepare activateClientTestPrepare;

    @Autowired
    private AddressClientTestPrepare addressClientTestPrepare;

    @Autowired
    private GetClientTestPrepare getClientTestPrepare;

    @Test
    void createNewClient_WithValidData_Returns201AndAccountData() throws Exception {
        final var mockResponse = mockResponse();
        when(service.createNewClient(any(ClientRequestDTO.class))).thenReturn(mockResponse);
        prepareAsserts.successPostAssert(requestValidMock(), status().isCreated(),
                jsonPath("$.agency").value(mockResponse.getAgency()),
                jsonPath("$.accountNumber").isNotEmpty(),
                jsonPath("$.provisionalPassword").isNotEmpty());
    }

    @Test
    void createNewClient_WithClientExistInDatabase_Returns409() throws Exception {
        final var failureResponse = CLIENT_ALREADY_EXISTS;
        when(service.createNewClient(any(ClientRequestDTO.class))).thenThrow(new ExistsClientRegistredException(failureResponse.getMessage()));
        prepareAsserts.failurePostAssert(requestValidMock(), failureResponse, status().isConflict());
    }

    @ParameterizedTest
    @MethodSource("provideRequestInvalidData")
    void createNewClient_WithInvalidData_Returns406(ClientRequest request) throws Exception {
        prepareAsserts.failurePostAssert(request, INVALID_DATA_MESSAGE, status().isNotAcceptable());
    }

    @Test
    void editClient_WithValidData_Returns200() throws Exception {
        prepareAsserts.successPutAssert(editClientRequestMock(), status().isOk());
    }

    @Test
    void editClient_WithClientNotExistInDatabase_Returns404() throws Exception {
        doThrow(new ClientNotExistsException(CLIENT_NOT_FOUND_MESSAGE.getMessage())).when(service).updateClient(any(EditClientInput.class));
        prepareAsserts.failurePutAssert(editClientRequestMock(), CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void editClient_WithClientAlreadyDeactivated_Returns409() throws Exception {
        doThrow(new ClientNotActiveException(CLIENT_ALREADY_DEACTIVATED.getMessage())).when(service).updateClient(any(EditClientInput.class));
        prepareAsserts.failurePutAssert(editClientRequestMock(), CLIENT_ALREADY_DEACTIVATED, status().isConflict());
    }

    @Test
    void deactivateClient_WithValidData_Returns200() throws Exception {
        deactivateRequestPrepare.successPostAssert(editStatusClientRequestMock(), status().isOk());
    }

    @Test
    void deactivateClient_WithInvalidData_Returns406() throws Exception {
        deactivateRequestPrepare.failurePostAssert(new EditStatusClientRequest(""), INVALID_DATA_MESSAGE, status().isNotAcceptable());
        deactivateRequestPrepare.failurePostAssert(new EditStatusClientRequest(null), INVALID_DATA_MESSAGE, status().isNotAcceptable());
    }

    @Test
    void deactivateClient_WithClientNotExistInDatabase_Returns404() throws Exception {
        doThrow(new ClientNotExistsException(CLIENT_NOT_FOUND_MESSAGE.getMessage())).when(service).deactivateClient(any(DeactivateClient.class));
        deactivateRequestPrepare.failurePostAssert(new EditStatusClientRequest("12345678901"), CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void deactivateClient_WithClientAlreadyDeactivated_Returns409() throws Exception {
        doThrow(new ClientNotActiveException(CLIENT_ALREADY_DEACTIVATED.getMessage())).when(service).deactivateClient(any(DeactivateClient.class));
        deactivateRequestPrepare.failurePostAssert(editStatusClientRequestMock(), CLIENT_ALREADY_DEACTIVATED, status().isConflict());
    }

    @Test
    void activateClient_WithValidData_Returns200() throws Exception {
        deactivateRequestPrepare.successPostAssert(activateClientMock(), status().isOk());
    }

    @Test
    void activateClient_WithInvalidData_Returns406() throws Exception {
        activateClientTestPrepare.failurePostAssert(new ActivateClient(""), INVALID_DATA_MESSAGE, status().isNotAcceptable());
        activateClientTestPrepare.failurePostAssert(new ActivateClient(null), INVALID_DATA_MESSAGE, status().isNotAcceptable());
    }

    @Test
    void activateClient_WithClientNotExistInDatabase_Returns404() throws Exception {
        doThrow(new ClientNotExistsException(CLIENT_NOT_FOUND_MESSAGE.getMessage())).when(service).activateClient(any(br.net.silva.daniel.value_object.input.ActivateClient.class));
        activateClientTestPrepare.failurePostAssert(new ActivateClient("12345678901"), CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void activateClient_WithClientAlreadyDeactivated_Returns409() throws Exception {
        doThrow(new ClientNotActiveException(CLIENT_ALREADY_DEACTIVATED.getMessage())).when(service).activateClient(any(br.net.silva.daniel.value_object.input.ActivateClient.class));
        activateClientTestPrepare.failurePostAssert(activateClientMock(), CLIENT_ALREADY_DEACTIVATED, status().isConflict());
    }

    @Test
    void updateAddress_WithValidData_Returns200() throws Exception {
        var request = new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", "city", "state", "zipCode");
        addressClientTestPrepare.successPutAssert(request, status().isOk());
    }

    @Test
    void updateAddress_WithClientNotExistInDatabase_Returns404() throws Exception {
        var request = new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", "city", "state", "zipCode");
        doThrow(new ClientNotExistsException(CLIENT_NOT_FOUND_MESSAGE.getMessage())).when(service).updateAddress(any(EditAddressInput.class));
        addressClientTestPrepare.failurePutAssert(request, CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void updateAddress_WithClientAlreadyDeactivated_Returns409() throws Exception {
        var request = new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", "city", "state", "zipCode");
        doThrow(new ClientNotActiveException(CLIENT_ALREADY_DEACTIVATED.getMessage())).when(service).updateAddress(any(EditAddressInput.class));
        addressClientTestPrepare.failurePutAssert(request, CLIENT_ALREADY_DEACTIVATED, status().isConflict());
    }

    @ParameterizedTest
    @MethodSource("provideAddressRequestInvalidData")
    void updateAddress_WithInvalidData_ReturnsStatus406(AddressRequest request) throws Exception {
        addressClientTestPrepare.failurePutAssert(request, INVALID_DATA_MESSAGE, status().isNotAcceptable());
    }

    @Test
    void getClient_WithValidData_ReturnsClient() throws Exception {
        var response = new GetInformationClientResponse();
        response.setName("Test");
        response.setCpf("00099988877");
        response.setTelephone("99999999999");
        response.setStreet("street");
        response.setNumber("number");
        response.setComplement("complement");
        response.setNeighborhood("neighborhood");
        response.setCity("city");
        response.setState("state");
        response.setZipCod("zipCode");
        when(service.getClientByCpf("00099988877")).thenReturn(response);
        getClientTestPrepare.successGetAssert(new Object[]{"00099988877"}, status().isOk(),
                jsonPath("$.cpf").value(response.getCpf()),
                jsonPath("$.name").value(response.getName()),
                jsonPath("$.telephone").value(response.getTelephone()),
                jsonPath("$.street").value(response.getStreet()),
                jsonPath("$.number").value(response.getNumber()),
                jsonPath("$.complement").value(response.getComplement()),
                jsonPath("$.neighborhood").value(response.getNeighborhood()),
                jsonPath("$.city").value(response.getCity()),
                jsonPath("$.state").value(response.getState()),
                jsonPath("$.zipCod").value(response.getZipCod()));
    }

    @Test
    void getClient_WithClientNotExists_ReturnsStatus404() throws Exception {
        when(service.getClientByCpf("00099988877")).thenThrow(new ClientNotExistsException("Client not exists in database"));
        getClientTestPrepare.failureGetAssert(new Object[]{"00099988877"}, CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    private NewAccountByNewClientResponseSuccess mockResponse() {
        final var response = new NewAccountByNewClientResponseSuccess();
        response.setAgency(1234);
        response.setAccountNumber(123456);
        response.setProvisionalPassword("test");
        return response;
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

    private static Stream<Arguments> provideAddressRequestInvalidData() {
        return Stream.of(
                Arguments.of(new AddressRequest("88899988800", "", "number", "complement", "neighborhood", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "", "complement", "neighborhood", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("", "street", "number", "complement", "neighborhood", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", "", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", "city", "", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", "city", "state", "")),
                Arguments.of(new AddressRequest("88899988800", null, "number", "complement", "neighborhood", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", null, "complement", "neighborhood", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest(null, "street", "number", "complement", "neighborhood", "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", null, "city", "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", null, "state", "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", "city", null, "zipCode")),
                Arguments.of(new AddressRequest("88899988800", "street", "number", "complement", "neighborhood", "city", "state", null))
        );
    }
}