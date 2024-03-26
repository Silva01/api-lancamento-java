package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.input.ChangePasswordDTO;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.utils.AccountUtils;
import br.net.silva.daniel.shared.application.value_object.Source;

public class ChangePasswordAccountUseCase implements UseCase<EmptyOutput> {
    private final UseCase<AccountOutput> findAccountUseCase;
    private final Repository<AccountOutput> updatePasswordRepository;
    private final IFactoryAggregate<Account, AccountDTO> createNewAccountByCpfFactory;

    public ChangePasswordAccountUseCase(UseCase<AccountOutput> findAccountUseCase, Repository<AccountOutput> updatePasswordRepository) {
        this.findAccountUseCase = findAccountUseCase;
        this.updatePasswordRepository = updatePasswordRepository;
        this.createNewAccountByCpfFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public EmptyOutput exec(Source param) throws GenericException {
        var changePasswordDTO = (ChangePasswordDTO) param.input();
        AccountUtils.validatePassword(changePasswordDTO.newPassword());
        var accountOutput = findAccountUseCase.exec(param);
        var account = createNewAccountByCpfFactory.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));
        account.changePassword(CryptoUtils.convertToSHA256(changePasswordDTO.newPassword()));
       updatePasswordRepository.exec(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));

       return EmptyOutput.INSTANCE;
    }
}
