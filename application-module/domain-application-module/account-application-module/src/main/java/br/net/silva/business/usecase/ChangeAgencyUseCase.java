package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.decorator.AccountAlreadyExistsForNewAgencyDecorator;
import br.net.silva.business.validations.AccountExistsValidate;
import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;

import java.util.Optional;

@ValidateStrategyOn(validations = {AccountExistsValidate.class})
public class ChangeAgencyUseCase implements UseCase<AccountOutput> {

    private final ApplicationBaseGateway<AccountOutput> baseGateway;

    private final IFactoryAggregate<Account, AccountDTO> factoryAggregate;

    public ChangeAgencyUseCase(ApplicationBaseGateway<AccountOutput> baseGateway) {
        this.baseGateway = baseGateway;
        this.factoryAggregate = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var changeAgencyInput = (ChangeAgencyInput) param.input();
        var accountOpt = baseGateway.findById(changeAgencyInput);

        final var accountOutput = execValidate(accountOpt,
                    new AccountAlreadyExistsForNewAgencyDecorator(getAccountWithNewAgency(changeAgencyInput)))
                .extract();

        var accountDto = AccountBuilder.buildFullAccountDto().createFrom(accountOutput);
        var account = factoryAggregate.create(accountDto);

        var newAccountDto = new AccountDTO(
                accountDto.number(),
                changeAgencyInput.newAgencyNumber(),
                accountDto.balance(),
                accountDto.password(),
                accountDto.active(),
                accountDto.cpf(),
                accountDto.transactions(),
                accountDto.creditCard()
        );

        account.deactivate();
        baseGateway.save(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));

        var newAccount = factoryAggregate.create(newAccountDto);
        return baseGateway.save(AccountBuilder.buildFullAccountOutput().createFrom(newAccount.build()));
    }

    private Optional<AccountOutput> getAccountWithNewAgency(ChangeAgencyInput changeAgencyInput) {
        var inputNewAgency = new ChangeAgencyInput(
                changeAgencyInput.cpf(),
                changeAgencyInput.accountNumber(),
                changeAgencyInput.newAgencyNumber(),
                changeAgencyInput.oldAgencyNumber());
        return baseGateway.findById(inputNewAgency);
    }
}
