package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.application.value_object.Source;

public class ChangeAgencyUseCase implements UseCase<EmptyOutput> {

    private final Repository<AccountOutput> findAccountByCpfAndAccountNumberRepository;
    private final Repository<AccountOutput> saveAccountRepository;

    private final IFactoryAggregate<Account, AccountDTO> factoryAggregate;

    public ChangeAgencyUseCase(Repository<AccountOutput> findAccountByCpfAndAccountNumberRepository, Repository<AccountOutput> saveAccountRepository) {
        this.findAccountByCpfAndAccountNumberRepository = findAccountByCpfAndAccountNumberRepository;
        this.saveAccountRepository = saveAccountRepository;
        this.factoryAggregate = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public EmptyOutput exec(Source param) throws GenericException {
        try {
            var changeAgencyInput = (ChangeAgencyInput) param.input();
            var accountOutput = findAccountByCpfAndAccountNumberRepository.exec(changeAgencyInput.cpf(), changeAgencyInput.accountNumber(), changeAgencyInput.oldAgencyNumber());

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
            saveAccountRepository.exec(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));

            var newAccount = factoryAggregate.create(newAccountDto);
            saveAccountRepository.exec(AccountBuilder.buildFullAccountOutput().createFrom(newAccount.build()));

            return EmptyOutput.INSTANCE;
        } catch (Exception e) {
            throw new GenericException("Generic error", e);
        }
    }
}
