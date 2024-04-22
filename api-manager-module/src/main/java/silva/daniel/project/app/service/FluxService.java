package silva.daniel.project.app.service;

import br.net.silva.business.build.AccountAlreadyExistsCreditCardValidationBuilder;
import br.net.silva.business.build.AccountExistsAndActiveValidationBuilder;
import br.net.silva.business.build.AccountExistsValidationBuilder;
import br.net.silva.business.build.AccountWithNewAgencyAlreadyExistsValidateBuilder;
import br.net.silva.business.build.CreditCardNumberExistsValidationBuilder;
import br.net.silva.business.usecase.ActivateAccountUseCase;
import br.net.silva.business.usecase.ChangeAgencyUseCase;
import br.net.silva.business.usecase.ChangePasswordAccountUseCase;
import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.usecase.CreateNewCreditCardUseCase;
import br.net.silva.business.usecase.DeactivateAccountUseCase;
import br.net.silva.business.usecase.DeactivateCreditCardUseCase;
import br.net.silva.business.usecase.FindAllAccountsByCpfUseCase;
import br.net.silva.business.usecase.GetInformationAccountUseCase;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.build.ClientExistsAndActivatedValidateBuilder;
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
                .withBuilderValidations()
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
                .withBuilderValidations()
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
                .withBuilderValidations()
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxActivateClient() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCases(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, ActivateClientUseCase.class)
                )
                .withBuilderValidations()
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxUpdateAddress() throws Exception {
        return FacadeBuilder
                .make().withBuilderUseCases(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, EditAddressUseCase.class)
                ).withBuilderValidations(
                        ValidationBuilder.create(ClientExistsValidateBuilder.class)
                                .withRepository(clientBaseRepository)
                ).build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxFindClient() throws Exception {
        return FacadeBuilder
                .make().withBuilderUseCases(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindClientUseCase.class)
                ).withBuilderValidations(
                        ValidationBuilder.create(ClientExistsValidateBuilder.class)
                                .withRepository(clientBaseRepository)
                ).build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxDeactivateCreditCard() throws Exception {
        return FacadeBuilder
                .make().withBuilderUseCases(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, DeactivateCreditCardUseCase.class)
                ).withBuilderValidations(
                        ValidationBuilder.create(ClientExistsValidateBuilder.class)
                                .withRepository(clientBaseRepository),
                        ValidationBuilder.create(AccountExistsValidationBuilder.class)
                                .withRepository(accountBaseRepository),
                        ValidationBuilder.create(CreditCardNumberExistsValidationBuilder.class)
                                .withRepository(accountBaseRepository)
                ).build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxCreateCreditCard() throws Exception {
        return FacadeBuilder
                .make().withBuilderUseCases(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, CreateNewCreditCardUseCase.class)
                ).withBuilderValidations(
                        ValidationBuilder.create(ClientExistsAndActivatedValidateBuilder.class)
                                .withRepository(clientBaseRepository),
                        ValidationBuilder.create(AccountExistsAndActiveValidationBuilder.class)
                                .withRepository(accountBaseRepository),
                        ValidationBuilder.create(AccountAlreadyExistsCreditCardValidationBuilder.class)
                                .withRepository(accountBaseRepository)
                ).build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxEditAgencyOfAccount() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCases(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, ChangeAgencyUseCase.class)
                )
                .withBuilderValidations(
                        ValidationBuilder.create(ClientExistsValidateBuilder.class)
                                .withRepository(clientBaseRepository),
                        ValidationBuilder.create(AccountExistsValidationBuilder.class)
                                .withRepository(accountBaseRepository),
                        ValidationBuilder.create(AccountWithNewAgencyAlreadyExistsValidateBuilder.class)
                                .withRepository(accountBaseRepository)
                )
                .build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxGetAccountByCpf() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCases(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, GetInformationAccountUseCase.class)
                ).withBuilderValidations().build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxGetAllAccount() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCases(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, FindAllAccountsByCpfUseCase.class)
                ).withBuilderValidations().build();
    }

    @SuppressWarnings("unchecked")
    public GenericFacadeDelegate fluxActivateAccount() throws Exception {
        return FacadeBuilder
                .make()
                .withBuilderUseCases(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class),
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, ActivateAccountUseCase.class)
                )
                .withBuilderValidations()
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
                .withBuilderValidations()
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
                .withBuilderValidations()
                .build();
    }
}
