package silva.daniel.project.app.config;

import br.net.silva.daniel.shared.application.build.UseCaseBuilder;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.repository.ApplicationBaseRepository;
import br.net.silva.daniel.usecase.CreateNewClientUseCase;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.springframework.stereotype.Component;

@Component
public class FluxComponent {

    private final GenericResponseMapper responseMapper;
    private final ApplicationBaseRepository<ClientOutput> baseRepository;

    public FluxComponent(GenericResponseMapper responseMapper, ApplicationBaseRepository<ClientOutput> baseRepository) {
        this.responseMapper = responseMapper;
        this.baseRepository = baseRepository;
    }

    public GenericFacadeDelegate<UseCase> fluxCreateNewClient() throws Exception {
        var findClientUseCase = UseCaseBuilder
                .make()
                .prepareUseCaseFrom(FindClientUseCase.class)
                .withBaseRepository(baseRepository)
                .withGenericMapper(responseMapper)
                .build();

        var createNewClientUseCase = UseCaseBuilder
                .make()
                .prepareUseCaseFrom(CreateNewClientUseCase.class)
                .withBaseRepository(baseRepository)
                .withGenericMapper(responseMapper)
                .build();


        return null;
    }
}
