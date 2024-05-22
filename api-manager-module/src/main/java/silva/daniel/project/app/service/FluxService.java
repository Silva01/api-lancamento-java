package silva.daniel.project.app.service;

import br.net.silva.business.usecase.ActivateAccountUseCase;
import br.net.silva.business.usecase.ChangeAgencyUseCase;
import br.net.silva.business.usecase.ChangePasswordAccountUseCase;
import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.usecase.CreateNewCreditCardUseCase;
import br.net.silva.business.usecase.DeactivateAccountUseCase;
import br.net.silva.business.usecase.DeactivateCreditCardUseCase;
import br.net.silva.business.usecase.FindAccountUseCase;
import br.net.silva.business.usecase.FindAllAccountsByCpfUseCase;
import br.net.silva.business.usecase.GetInformationAccountUseCase;
import br.net.silva.business.usecase.RegisterTransactionUseCase;
import br.net.silva.business.usecase.ReversalTransactionUseCase;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.build.FacadeBuilder;
import br.net.silva.daniel.shared.application.build.UseCaseBuilder;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.usecase.ActivateClientUseCase;
import br.net.silva.daniel.usecase.CreateNewClientUseCase;
import br.net.silva.daniel.usecase.DeactivateClientUseCase;
import br.net.silva.daniel.usecase.EditAddressUseCase;
import br.net.silva.daniel.usecase.EditClientUseCase;
import br.net.silva.daniel.usecase.FindActiveClientUseCase;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.springframework.stereotype.Service;

@Service
public class FluxService {

    private final GenericResponseMapper responseMapper;
    private final ApplicationBaseGateway<ClientOutput> clientBaseRepository;
    private final ApplicationBaseGateway<AccountOutput> accountBaseRepository;
    private final ApplicationBaseGateway<BatchTransactionInput> transactionBaseRepository;
    private final ApplicationBaseGateway<ReversalTransactionInput> reversalTransactionGateway;

    public FluxService(GenericResponseMapper responseMapper, ApplicationBaseGateway<ClientOutput> clientBaseRepository, ApplicationBaseGateway<AccountOutput> accountBaseRepository, ApplicationBaseGateway<BatchTransactionInput> transactionBaseRepository, ApplicationBaseGateway<ReversalTransactionInput> reversalTransactionGateway) {
        this.responseMapper = responseMapper;
        this.clientBaseRepository = clientBaseRepository;
        this.accountBaseRepository = accountBaseRepository;
        this.transactionBaseRepository = transactionBaseRepository;
        this.reversalTransactionGateway = reversalTransactionGateway;
    }

    public GenericFacadeDelegate fluxCreateNewClient() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, CreateNewClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, CreateNewAccountByCpfUseCase.class))
                .build();

    }

    public GenericFacadeDelegate fluxUpdateClient() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder
                                .makeTo(clientBaseRepository, responseMapper, EditClientUseCase.class)
                ).build();
    }

    public GenericFacadeDelegate fluxDeactivateClient() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, DeactivateClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, DeactivateAccountUseCase.class))
                .build();
    }

    public GenericFacadeDelegate fluxActivateClient() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, ActivateClientUseCase.class)
                )
                .build();
    }

    public GenericFacadeDelegate fluxUpdateAddress() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, EditAddressUseCase.class)
                )
                .build();
    }

    public GenericFacadeDelegate fluxFindClient() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindClientUseCase.class)
                )
                .build();
    }

    public GenericFacadeDelegate fluxDeactivateCreditCard() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, DeactivateCreditCardUseCase.class))
                .build();
    }

    public GenericFacadeDelegate fluxCreateCreditCard() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, CreateNewCreditCardUseCase.class))
                .build();
    }

    public GenericFacadeDelegate fluxEditAgencyOfAccount() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, ChangeAgencyUseCase.class))
                .build();
    }

    public GenericFacadeDelegate fluxGetAccountByCpf() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, GetInformationAccountUseCase.class)
                ).build();
    }

    public GenericFacadeDelegate fluxGetAllAccount() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, FindAllAccountsByCpfUseCase.class)
                ).build();
    }

    public GenericFacadeDelegate fluxActivateAccount() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, ActivateAccountUseCase.class))
                .build();
    }

    public GenericFacadeDelegate fluxDeactivateAccount() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, DeactivateAccountUseCase.class))
                .build();
    }

    public GenericFacadeDelegate fluxChangePassword() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, ChangePasswordAccountUseCase.class))
                .build();
    }

    public GenericFacadeDelegate fluxCreateNewAccount() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class)
                )
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, CreateNewAccountByCpfUseCase.class)
                )
                .build();
    }

    public GenericFacadeDelegate fluxRegisterTransaction() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class)
                )
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, FindAccountUseCase.class)
                )
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(transactionBaseRepository, responseMapper, RegisterTransactionUseCase.class)
                )
                .build();
    }

    public GenericFacadeDelegate fluxRefundTransaction() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(reversalTransactionGateway, responseMapper, ReversalTransactionUseCase.class)
                )
                .build();
    }
}
