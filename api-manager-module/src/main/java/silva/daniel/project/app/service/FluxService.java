package silva.daniel.project.app.service;

import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.usecase.DeactivateAccountUseCase;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.build.ClientExistsAndDeactivatedValidateBuilder;
import br.net.silva.daniel.build.ClientExistsValidateBuilder;
import br.net.silva.daniel.build.ClientNotExistsValidateBuilder;
import br.net.silva.daniel.shared.application.build.FacadeBuilder;
import br.net.silva.daniel.shared.application.build.UseCaseBuilder;
import br.net.silva.daniel.shared.application.build.ValidationBuilder;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.usecase.ActivateClientUseCase;
import br.net.silva.daniel.usecase.CreateNewClientUseCase;
import br.net.silva.daniel.usecase.DeactivateClientUseCase;
import br.net.silva.daniel.usecase.EditClientUseCase;
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
                .withBuilderUseCases(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, CreateNewClientUseCase.class),
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, CreateNewAccountByCpfUseCase.class))
                .withBuilderValidations(
                        ValidationBuilder
                                .create(ClientNotExistsValidateBuilder.class)
                                .withRepository(clientBaseRepository)
                )
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxUpdateClient() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCases(
                        UseCaseBuilder
                                .makeTo(clientBaseRepository, responseMapper, EditClientUseCase.class)
                )
                .withBuilderValidations(
                        ValidationBuilder.create(ClientExistsValidateBuilder.class)
                                .withRepository(clientBaseRepository)
                )
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxDeactivateClient() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCases(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, DeactivateClientUseCase.class),
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, DeactivateAccountUseCase.class)
                )
                .withBuilderValidations(
                        ValidationBuilder
                                .create(ClientExistsValidateBuilder.class)
                                .withRepository(clientBaseRepository)
                )
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxActivateClient() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCases(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, ActivateClientUseCase.class)
                )
                .withBuilderValidations(
                        ValidationBuilder.create(ClientExistsAndDeactivatedValidateBuilder.class)
                                .withRepository(clientBaseRepository)
                )
                .build();
    }
}
