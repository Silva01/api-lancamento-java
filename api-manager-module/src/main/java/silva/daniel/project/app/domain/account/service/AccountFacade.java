package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.usecase.ActivateAccountUseCase;
import br.net.silva.business.usecase.ChangeAgencyUseCase;
import br.net.silva.business.usecase.ChangePasswordAccountUseCase;
import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.usecase.DeactivateAccountUseCase;
import br.net.silva.business.usecase.FindAllAccountsByCpfUseCase;
import br.net.silva.business.usecase.GetInformationAccountUseCase;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.build.FacadeBuilder;
import br.net.silva.daniel.shared.application.build.UseCaseBuilder;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.usecase.FindActiveClientUseCase;
import br.net.silva.daniel.value_object.output.ClientOutput;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountFacade {

    private final GenericResponseMapper responseMapper;
    private final ApplicationBaseGateway<ClientOutput> clientBaseRepository;
    private final ApplicationBaseGateway<AccountOutput> accountBaseRepository;

    public GenericFacadeDelegate editAgencyOfAccount() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, ChangeAgencyUseCase.class))
                .build();
    }

    public GenericFacadeDelegate getAccountByCpf() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, GetInformationAccountUseCase.class)
                ).build();
    }

    public GenericFacadeDelegate getAllAccount() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, FindAllAccountsByCpfUseCase.class)
                ).build();
    }

    public GenericFacadeDelegate activateAccount() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, ActivateAccountUseCase.class))
                .build();
    }

    public GenericFacadeDelegate deactivateAccount() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, DeactivateAccountUseCase.class))
                .build();
    }

    public GenericFacadeDelegate changePassword() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class))
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, ChangePasswordAccountUseCase.class))
                .build();
    }

    public GenericFacadeDelegate createNewAccount() {
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
}
