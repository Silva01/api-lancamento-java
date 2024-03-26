package br.net.silva.business.validations;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.utils.AccountUtils;
import br.net.silva.daniel.shared.application.value_object.Source;

//TODO: Precisa refatorar para receber um repository
public class PasswordAndExistsAccountValidate implements IValidations {

    private final UseCase<AccountOutput> findAccountUseCase;
    private final CreateAccountByAccountDTOFactory factory;

    public PasswordAndExistsAccountValidate(UseCase<AccountOutput> findAccountUseCase) {
        this.findAccountUseCase = findAccountUseCase;
        this.factory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public void validate(Source input) throws GenericException {
        var findAccountParamDTO = (IAccountParam) input.input();
        AccountUtils.validatePassword(findAccountParamDTO.password());
        var accountOutput = findAccountUseCase.exec(input);
        var account = factory.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));
        account.validatePassword(CryptoUtils.convertToSHA256(findAccountParamDTO.password()));
    }
}
