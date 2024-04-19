package br.net.silva.business.facade;

import br.net.silva.business.exception.AccountExistsForCPFInformatedException;
import br.net.silva.business.mapper.CreateResponseToNewAccountByClientFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountFactory;
import br.net.silva.business.usecase.ChangePasswordAccountUseCase;
import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.usecase.DeactivateAccountUseCase;
import br.net.silva.business.validations.PasswordAndExistsAccountValidate;
import br.net.silva.business.value_object.input.ChangePasswordDTO;
import br.net.silva.business.value_object.input.CreateNewAccountByCpfDTO;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.NewAccountByNewClientResponseSuccess;
import br.net.silva.business.value_object.output.NewAccountResponse;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.exception.PasswordDivergentException;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountFacadeTest {

    private UseCase<AccountOutput> createNewAccountByCpfUseCase;

    private UseCase<AccountOutput> changePasswordAccountUseCase;

    private UseCase<AccountOutput> deactivateAccountUseCase;

    private IValidations passwordAndExistsAccountValidate;

    private GenericResponseMapper factory;
    @Mock
    private ApplicationBaseGateway<AccountOutput> baseAccountGateway;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        factory = new GenericResponseMapper(List.of(new CreateResponseToNewAccountFactory(), new CreateResponseToNewAccountByClientFactory()));
        createNewAccountByCpfUseCase = new CreateNewAccountByCpfUseCase(baseAccountGateway, factory);
        passwordAndExistsAccountValidate = new PasswordAndExistsAccountValidate(baseAccountGateway);
        changePasswordAccountUseCase = new ChangePasswordAccountUseCase(baseAccountGateway);
        this.deactivateAccountUseCase = new DeactivateAccountUseCase(baseAccountGateway);
    }

    @Test
    void mustCreateNewAccountByCpf() throws GenericException {
        when(baseAccountGateway.findById(any(ICpfParam.class))).thenReturn(Optional.empty());
        when(baseAccountGateway.save(any(AccountOutput.class))).thenReturn(buildMockAccount());
        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "978534");
        var source = new Source(new NewAccountResponse(), createNewAccountByCpfDTO);

        accountFacade.exec(source);

        assertNotNull(source.output());
        var accountDTo = buildMockAccount();

        var response = (NewAccountResponse) source.output();

        assertEquals(accountDTo.agency(), response.getAgency());
        assertNotNull(response.getAccountNumber());
    }

    @Test
    void mustCreateNewAccountByCpfWithObjectResponseOfClient() throws GenericException {
        when(baseAccountGateway.findById(any(ICpfParam.class))).thenReturn(Optional.empty());
        when(baseAccountGateway.save(any(AccountOutput.class))).thenReturn(buildMockAccount());
        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "978534");
        var source = new Source(new NewAccountByNewClientResponseSuccess(), createNewAccountByCpfDTO);

        accountFacade.exec(source);

        assertNotNull(source.output());
        var accountDTo = buildMockAccount();

        var response = (NewAccountByNewClientResponseSuccess) source.output();

        assertEquals(accountDTo.agency(), response.getAgency());
        assertNotNull(response.getAccountNumber());
        assertNotNull(response.getAccountNumber());
        assertNotNull(response.getProvisionalPassword());
    }

    @Test
    void mustErrorCreateNewAccountByCpfAccountNotExists() {
        when(baseAccountGateway.findById(any(ICpfParam.class))).thenReturn(Optional.of(buildMockAccount()));
        when(baseAccountGateway.save(any(AccountOutput.class))).thenReturn(buildMockAccount());
        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "978534");
        var source = new Source(EmptyOutput.INSTANCE, createNewAccountByCpfDTO);

        var exceptionResponse = assertThrows(AccountExistsForCPFInformatedException.class, () -> accountFacade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Account already exists for the CPF informed", exceptionResponse.getMessage());
    }

    @Test
    void mustErrorCreateNewAccountByCpfAccountNotExistsValidation() {
        when(baseAccountGateway.findById(any(ICpfParam.class))).thenReturn(Optional.of(buildMockAccount()));
        when(baseAccountGateway.save(any(AccountOutput.class))).thenReturn(buildMockAccount());
        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "978534");
        var source = new Source(EmptyOutput.INSTANCE, createNewAccountByCpfDTO);

        var exceptionResponse = assertThrows(AccountExistsForCPFInformatedException.class, () -> accountFacade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Account already exists for the CPF informed", exceptionResponse.getMessage());
    }

    @Test
    void mustErrorCreateNewAccountByCpfPasswordInvalid() {
        when(baseAccountGateway.findById(any(ICpfParam.class))).thenReturn(Optional.of(buildMockAccount()));
        when(baseAccountGateway.save(any(AccountOutput.class))).thenReturn(buildMockAccount());
        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewAccountByCpfUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        CreateNewAccountByCpfDTO createNewAccountByCpfDTO = new CreateNewAccountByCpfDTO("123456", 1222, "123456");
        var source = new Source(EmptyOutput.INSTANCE, createNewAccountByCpfDTO);

        var exceptionResponse = assertThrows(PasswordDivergentException.class, () -> accountFacade.exec(source));
        assertNotNull(exceptionResponse);
        assertEquals("Password is different", exceptionResponse.getMessage());
    }

    @Test
    void mustChangePasswordWithSuccess() throws GenericException {
        when(baseAccountGateway.findById(any(ICpfParam.class))).thenReturn(Optional.of(buildMockAccount()));
        when(baseAccountGateway.save(any(AccountOutput.class))).thenReturn(buildMockAccount());

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(changePasswordAccountUseCase);

        List<IValidations> validationsList = List.of(passwordAndExistsAccountValidate);

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("99988877766", 45678, 1, "978534", "123456");
        var source = new Source(EmptyOutput.INSTANCE, changePasswordDTO);
        accountFacade.exec(source);

        assertNotNull(source.output());
    }

    @Test
    void mustDeactivateAccountWithSuccess() throws GenericException {
        when(baseAccountGateway.findById(any(ParamGateway.class))).thenReturn(Optional.of(buildMockAccount()));
        when(baseAccountGateway.save(any(AccountOutput.class))).thenReturn(buildMockAccount());

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(deactivateAccountUseCase);

        List<IValidations> validationsList = Collections.emptyList();

        var accountFacade = new GenericFacadeDelegate(useCases, validationsList);
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO("99988877766", 45678, 1, "978534", "123456");
        var source = new Source(EmptyOutput.INSTANCE, changePasswordDTO);

        accountFacade.exec(source);
        assertNotNull(source.output());
    }

    private AccountOutput buildMockAccount() {
        return new AccountOutput(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), true, "99988877766", null, Collections.emptyList());
    }
}