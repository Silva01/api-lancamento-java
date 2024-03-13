package silva.daniel.project.app.config;

import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.daniel.build.ClientNotExistsValidateBuilder;
import br.net.silva.daniel.shared.application.build.FacadeBuilder;
import br.net.silva.daniel.shared.application.build.UseCaseBuilder;
import br.net.silva.daniel.shared.application.build.ValidationBuilder;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.repository.ApplicationBaseRepository;
import br.net.silva.daniel.usecase.CreateNewClientUseCase;
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

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxCreateNewClient() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCases(
                        UseCaseBuilder.makeTo(baseRepository, responseMapper, CreateNewClientUseCase.class),
                        UseCaseBuilder.makeTo(baseRepository, responseMapper, CreateNewAccountByCpfUseCase.class))
                .withBuilderValidations(ValidationBuilder.create(ClientNotExistsValidateBuilder.class))
                .build();
    }
}
