package silva.daniel.project.app.web;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.exception.CreditCardDeactivatedException;
import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.daniel.exception.ClientNotExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import silva.daniel.project.app.commons.RequestAssertCommons;
import silva.daniel.project.app.commons.RequestBuilderCommons;
import silva.daniel.project.app.domain.account.request.CreateCreditCardRequest;
import silva.daniel.project.app.domain.account.request.DeactivateCreditCardRequest;
import silva.daniel.project.app.domain.account.service.CreditCardService;
import silva.daniel.project.app.web.account.CreateCreditCardPrepare;
import silva.daniel.project.app.web.account.DeactivateCreditCardPrepare;
import silva.daniel.project.app.web.account.annotations.EnableCreditCardPrepare;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static silva.daniel.project.app.commons.FailureMessageEnum.ACCOUNT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CLIENT_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CREDIT_CARD_ALREADY_DEACTIVATED_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.CREDIT_CARD_NOT_FOUND_MESSAGE;
import static silva.daniel.project.app.commons.FailureMessageEnum.INVALID_DATA_MESSAGE;

@ActiveProfiles("unit")
@EnableCreditCardPrepare
class CreditCardControllerTest extends RequestAssertCommons implements RequestBuilderCommons {
    
    @MockBean
    private CreditCardService service;

    @Autowired
    private CreateCreditCardPrepare createCreditCardPrepare;

    @Autowired
    private DeactivateCreditCardPrepare deactivateCreditCardPrepare;

    @Test
    void deactivateCreditCard_WithValidData_ReturnsSuccess() throws Exception {
        deactivateCreditCardPrepare.successPostAssert(buildBaseRequest(), status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidDeactivateCreditCardRequests")
    @DisplayName("Deactivate credit card with invalid data returns exception")
    void deactivateCreditCard_WithInvalidData_ReturnsException(DeactivateCreditCardRequest request) throws Exception {
        deactivateCreditCardPrepare.failurePostAssert(request, INVALID_DATA_MESSAGE, status().isNotAcceptable());
    }

    @Test
    void deactivateCreditCard_WithClientNotExists_ReturnsStatus404() throws Exception {
        doThrow(new ClientNotExistsException("Client not exists")).when(service).deactivateCreditCard(any(DeactivateCreditCardInput.class));
        failurePostAssert(buildBaseRequest(), CLIENT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void deactivateCreditCard_WithAccountNotExists_ReturnsStatus404() throws Exception {
        doThrow(new AccountNotExistsException("Account not exists")).when(service).deactivateCreditCard(any(DeactivateCreditCardInput.class));
        failurePostAssert(buildBaseRequest(), ACCOUNT_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void deactivateCreditCard_WithCreditCardNotFound_ReturnsStatus404() throws Exception {
        doThrow(new CreditCardNotExistsException("Credit card not exists in the account")).when(service).deactivateCreditCard(any(DeactivateCreditCardInput.class));
        failurePostAssert(buildBaseRequest(), CREDIT_CARD_NOT_FOUND_MESSAGE, status().isNotFound());
    }

    @Test
    void deactivateCreditCard_WithCreditCardDeactivated_ReturnsStatus409() throws Exception {
        doThrow(new CreditCardDeactivatedException("Credit card deactivated in the account")).when(service).deactivateCreditCard(any(DeactivateCreditCardInput.class));
        failurePostAssert(buildBaseRequest(), CREDIT_CARD_ALREADY_DEACTIVATED_MESSAGE, status().isConflict());
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

    @Test
    void createCreditCard_WithValidData_ReturnsStatus201() throws Exception {
        var request = new CreateCreditCardRequest("12345678901", 123456, 1234);
        createCreditCardPrepare.successPostAssert(request, status().isCreated());
    }

    @Override
    public String url() {
        return "/credit-card/deactivate";
    }
}