package br.net.silva.business.usecase;

import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.mapper.MapToChangePasswordMapper;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.utils.AccountUtils;
import br.net.silva.daniel.value_object.Source;

public class ChangePasswordAccountUseCase implements UseCase {
    private final UseCase findAccountUseCase;
    private final Repository<Account> updatePasswordRepository;
    private final MapToChangePasswordMapper mapper;
    private final IFactoryAggregate<Account, AccountDTO> createNewAccountByCpfFactory;

    public ChangePasswordAccountUseCase(UseCase findAccountUseCase, Repository<Account> updatePasswordRepository) {
        this.mapper = MapToChangePasswordMapper.INSTANCE;
        this.findAccountUseCase = findAccountUseCase;
        this.updatePasswordRepository = updatePasswordRepository;
        this.createNewAccountByCpfFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public void exec(Source param) throws GenericException {
        var changePasswordDTO = mapper.mapToChangePasswordDto(param.input());
        AccountUtils.validatePassword(changePasswordDTO.newPassword());
        findAccountUseCase.exec(param);
        var account = createNewAccountByCpfFactory.create((AccountDTO) param.map().get(TypeAccountMapperEnum.ACCOUNT.name()));
        account.changePassword(CryptoUtils.convertToSHA256(changePasswordDTO.newPassword()));
        var accountUpdated = updatePasswordRepository.exec(account);
        param.map().put(TypeAccountMapperEnum.ACCOUNT.name(), accountUpdated.build());
    }
}
