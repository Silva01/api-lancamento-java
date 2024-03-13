package silva.daniel.project.app.config;

import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.build.ClientNotExistsValidateBuilder;
import br.net.silva.daniel.shared.application.build.FacadeBuilder;
import br.net.silva.daniel.shared.application.build.UseCaseBuilder;
import br.net.silva.daniel.shared.application.build.ValidationBuilder;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseRepository;
import br.net.silva.daniel.usecase.CreateNewClientUseCase;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.springframework.stereotype.Component;

@Component
public class FluxComponent {

    private final GenericResponseMapper responseMapper;
    private final ApplicationBaseRepository<ClientOutput> clientBaseRepository;
    private final ApplicationBaseRepository<AccountOutput> accountBaseRepository;

    public FluxComponent(GenericResponseMapper responseMapper, ApplicationBaseRepository<ClientOutput> clientBaseRepository, ApplicationBaseRepository<AccountOutput> accountBaseRepository) {
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
}
