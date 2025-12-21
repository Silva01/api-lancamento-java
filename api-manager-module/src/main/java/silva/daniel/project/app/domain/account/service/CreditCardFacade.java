package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.usecase.CreateNewCreditCardUseCase;
import br.net.silva.business.usecase.DeactivateCreditCardUseCase;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.build.FacadeBuilder;
import br.net.silva.daniel.shared.application.build.UseCaseBuilder;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.usecase.FindActiveClientUseCase;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.value_object.output.ClientOutput;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreditCardFacade {

    private final GenericResponseMapper responseMapper;
    private final ApplicationBaseGateway<ClientOutput> clientBaseRepository;
    private final ApplicationBaseGateway<AccountOutput> accountBaseRepository;

    public GenericFacadeDelegate deactivateCreditCard() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, DeactivateCreditCardUseCase.class))
                .build();
    }

    public GenericFacadeDelegate createCreditCard() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, CreateNewCreditCardUseCase.class))
                .build();
    }
}
