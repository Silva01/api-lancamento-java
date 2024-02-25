package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.ChangePasswordDTO;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.utils.AccountUtils;
import br.net.silva.daniel.value_object.Source;

public class ChangePasswordAccountUseCase implements UseCase<EmptyOutput> {
    private final UseCase<AccountDTO> findAccountUseCase;
    private final Repository<Account> updatePasswordRepository;
    private final IFactoryAggregate<Account, AccountDTO> createNewAccountByCpfFactory;

    public ChangePasswordAccountUseCase(UseCase<AccountDTO> findAccountUseCase, Repository<Account> updatePasswordRepository) {
        this.findAccountUseCase = findAccountUseCase;
        this.updatePasswordRepository = updatePasswordRepository;
        this.createNewAccountByCpfFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public EmptyOutput exec(Source param) throws GenericException {
        var changePasswordDTO = (ChangePasswordDTO) param.input();
        AccountUtils.validatePassword(changePasswordDTO.newPassword());
        var accountDTO = findAccountUseCase.exec(param);
        var account = createNewAccountByCpfFactory.create(accountDTO);
        account.changePassword(CryptoUtils.convertToSHA256(changePasswordDTO.newPassword()));
       updatePasswordRepository.exec(account);

       return EmptyOutput.INSTANCE;
    }
}
