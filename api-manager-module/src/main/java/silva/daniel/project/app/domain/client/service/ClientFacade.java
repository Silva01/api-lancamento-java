package silva.daniel.project.app.domain.client.service;

import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.usecase.DeactivateAccountUseCase;
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
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.value_object.output.ClientOutput;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClientFacade {

    private final GenericResponseMapper responseMapper;
    private final ApplicationBaseGateway<ClientOutput> clientBaseRepository;
    private final ApplicationBaseGateway<AccountOutput> accountBaseRepository;

    public GenericFacadeDelegate createNewClient() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, CreateNewClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, CreateNewAccountByCpfUseCase.class))
                .build();

    }

    public GenericFacadeDelegate updateClient() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder
                                .makeTo(clientBaseRepository, responseMapper, EditClientUseCase.class)
                ).build();
    }

    public GenericFacadeDelegate deactivateClient() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, DeactivateClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, DeactivateAccountUseCase.class))
                .build();
    }

    public GenericFacadeDelegate activateClient() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, ActivateClientUseCase.class)
                )
                .build();
    }

    public GenericFacadeDelegate updateAddress() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, EditAddressUseCase.class)
                )
                .build();
    }

    public GenericFacadeDelegate findClient() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindClientUseCase.class)
                )
                .build();
    }
}
