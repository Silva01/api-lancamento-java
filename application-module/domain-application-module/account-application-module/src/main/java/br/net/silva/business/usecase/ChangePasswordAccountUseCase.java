package br.net.silva.business.usecase;

import br.net.silva.business.dto.ChangePasswordDTO;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.interfaces.IProcessResponse;
import br.net.silva.daniel.shared.business.mapper.GenericMapper;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.utils.AccountUtils;

public class ChangePasswordAccountUseCase implements UseCase<IProcessResponse<AccountDTO>> {

    private final GenericMapper<ChangePasswordDTO> mapper;
    private final UseCase<IProcessResponse<AccountDTO>> findAccountUseCase;
    private final Repository<Account> updatePasswordRepository;

    public ChangePasswordAccountUseCase(UseCase<IProcessResponse<AccountDTO>> findAccountUseCase, Repository<Account> updatePasswordRepository) {
        this.mapper = new GenericMapper<>(ChangePasswordDTO.class);
        this.findAccountUseCase = findAccountUseCase;
        this.updatePasswordRepository = updatePasswordRepository;
    }

    @Override
    public IProcessResponse<AccountDTO> exec(IGenericPort dto) throws GenericException {
        var changePasswordDTO = mapper.map(dto);
        AccountUtils.validatePassword(changePasswordDTO.newPassword());
        var account = (Account) findAccountUseCase.exec(changePasswordDTO);
        account.changePassword(CryptoUtils.convertToSHA256(changePasswordDTO.newPassword()));
        return updatePasswordRepository.exec(account);
    }
}
