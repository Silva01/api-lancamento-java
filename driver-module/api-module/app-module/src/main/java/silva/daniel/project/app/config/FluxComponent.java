package silva.daniel.project.app.config;

import br.net.silva.business.build.CreateNewAccountByCpfUseCaseBuilder;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.build.*;
import br.net.silva.daniel.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.output.ClientOutput;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Component
public class FluxComponent {

    private final GenericResponseMapper responseMapper;
    private final Repository<ClientOutput> saveRepository;
    private final Repository<Boolean> findIsExistsPeerCPFRepository;
    private final Repository<AccountOutput> saveAccountRepository;
    private final Repository<Optional<ClientOutput>> findClientRepository;

    public FluxComponent(GenericResponseMapper responseMapper, Repository<ClientOutput> saveRepository, Repository<Boolean> findIsExistsPeerCPFRepository, Repository<AccountOutput> saveAccountRepository, Repository<Optional<ClientOutput>> findClientRepository) {
        this.responseMapper = responseMapper;
        this.saveRepository = saveRepository;
        this.findIsExistsPeerCPFRepository = findIsExistsPeerCPFRepository;
        this.saveAccountRepository = saveAccountRepository;
        this.findClientRepository = findClientRepository;
    }

    public GenericFacadeDelegate<UseCase> fluxCreateNewClient() throws Exception {
        var createNewClientUseCase = UseCaseBuilder
                .buildUseCase(CreateNewClientUseCaseBuilder.class)
                .withRepository(saveRepository)
                .withFactory(responseMapper)
                .build();

        var createNewAccountByCpfUseCase = UseCaseBuilder
                .buildUseCase(CreateNewAccountByCpfUseCaseBuilder.class)
                .withRepositoryForFind(findIsExistsPeerCPFRepository)
                .withRepositoryForSave(saveAccountRepository)
                .withFactory(responseMapper)
                .build();

        var findClientUseCase = UseCaseBuilder
                .buildUseCase(FindClientUseCaseBuilder.class)
                .withRepository(findClientRepository)
                .withFactory(responseMapper)
                .build();

        var validation = ValidationBuilder
                .create(ClientNotExistsValidateBuilder.class)
                .withUseCase(findClientUseCase)
                .build();

        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(createNewClientUseCase);
        useCases.add(createNewAccountByCpfUseCase);

        return new GenericFacadeDelegate<>(useCases, List.of(validation));
    }
}
