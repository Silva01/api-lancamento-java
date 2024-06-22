package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.validations.AccountExistsAndActiveValidate;
import br.net.silva.business.value_object.input.ChangePasswordDTO;
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
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.utils.AccountUtils;

@ValidateStrategyOn(validations = {AccountExistsAndActiveValidate.class})
public class ChangePasswordAccountUseCase implements UseCase<AccountOutput> {
    private final ApplicationBaseGateway<AccountOutput> baseAccountGateway;
    private final IFactoryAggregate<Account, AccountDTO> createNewAccountByCpfFactory;

    public ChangePasswordAccountUseCase(ApplicationBaseGateway<AccountOutput> baseAccountGateway) {
        this.baseAccountGateway = baseAccountGateway;
        this.createNewAccountByCpfFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var changePasswordDTO = (ChangePasswordDTO) param.input();
        AccountUtils.validatePassword(changePasswordDTO.newPassword());
        var accountOpt = baseAccountGateway.findById(changePasswordDTO);

        var accountOutput = execValidate(accountOpt).extract();

        var account = createNewAccountByCpfFactory.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));
        account.validatePassword(CryptoUtils.convertToSHA256(changePasswordDTO.password()));
        account.changePassword(CryptoUtils.convertToSHA256(changePasswordDTO.newPassword()));
        baseAccountGateway.save(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));
        return null;
    }
}
