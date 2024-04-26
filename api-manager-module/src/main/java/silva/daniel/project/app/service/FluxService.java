package silva.daniel.project.app.service;

import br.net.silva.business.usecase.*;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.build.FacadeBuilder;
import br.net.silva.daniel.shared.application.build.UseCaseBuilder;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.usecase.*;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.springframework.stereotype.Service;

@Service
public class FluxService {

    private final GenericResponseMapper responseMapper;
    private final ApplicationBaseGateway<ClientOutput> clientBaseRepository;
    private final ApplicationBaseGateway<AccountOutput> accountBaseRepository;

    public FluxService(GenericResponseMapper responseMapper, ApplicationBaseGateway<ClientOutput> clientBaseRepository, ApplicationBaseGateway<AccountOutput> accountBaseRepository) {
        this.responseMapper = responseMapper;
        this.clientBaseRepository = clientBaseRepository;
        this.accountBaseRepository = accountBaseRepository;
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxCreateNewClient() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, CreateNewClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, CreateNewAccountByCpfUseCase.class))
                .build();

    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxUpdateClient() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder
                                .makeTo(clientBaseRepository, responseMapper, EditClientUseCase.class)
                ).build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxDeactivateClient() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, DeactivateClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, DeactivateAccountUseCase.class))
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxActivateClient() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, ActivateClientUseCase.class)
                )
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxUpdateAddress() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, EditAddressUseCase.class)
                )
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxFindClient() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindClientUseCase.class)
                )
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxDeactivateCreditCard() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, DeactivateCreditCardUseCase.class))
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxCreateCreditCard() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, CreateNewCreditCardUseCase.class))
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxEditAgencyOfAccount() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCases(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class),
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, ChangeAgencyUseCase.class)
                )
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxGetAccountByCpf() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, GetInformationAccountUseCase.class)
                ).build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxGetAllAccount() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, FindAllAccountsByCpfUseCase.class)
                ).build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxActivateAccount() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCases(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class),
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, ActivateAccountUseCase.class)
                )
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxDeactivateAccount() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCases(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class),
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, DeactivateAccountUseCase.class)
                )
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxChangePassword() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCases(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class),
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, ChangePasswordAccountUseCase.class)
                )
                .build();
    }
}
