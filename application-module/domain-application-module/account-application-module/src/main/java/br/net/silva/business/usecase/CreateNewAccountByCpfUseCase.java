package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.validations.AccountNotExistsValidation;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.SaveApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IAgencyParam;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

@ValidateStrategyOn(validations = {AccountNotExistsValidation.class})
public class CreateNewAccountByCpfUseCase implements UseCase<AccountOutput> {

    private final FindApplicationBaseGateway<AccountOutput> findIsExistsPeerCPFRepository;
    private final SaveApplicationBaseGateway<AccountOutput> saveRepository;
    private final GenericResponseMapper factory;

    public CreateNewAccountByCpfUseCase(ApplicationBaseGateway<AccountOutput> baseRepository, GenericResponseMapper factory) {
        this.findIsExistsPeerCPFRepository = baseRepository;
        this.saveRepository = baseRepository;
        this.factory = factory;
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var clientCpf = (ICpfParam) param.input();

        execValidate(findIsExistsPeerCPFRepository.findById(clientCpf));

        var accountOutput = save(createAccount((IAgencyParam) param.input(), clientCpf));
        createResponse(param, accountOutput);
        return accountOutput;
    }

    private void createResponse(Source param, AccountOutput accountOutput) {
        factory.fillIn(AccountBuilder.buildFullAccountDto().createFrom(accountOutput), param.output());
    }

    private AccountOutput save(Account newAccount) {
        return saveRepository.save(AccountBuilder.buildFullAccountOutput().createFrom(newAccount.build()));
    }

    private static Account createAccount(IAgencyParam agencyInterface, ICpfParam clientCpf) {
        return new Account(agencyInterface.agency(), "default", clientCpf.cpf());
    }
}
