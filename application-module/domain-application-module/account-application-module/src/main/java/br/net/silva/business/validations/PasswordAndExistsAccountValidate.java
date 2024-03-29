package br.net.silva.business.validations;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.utils.AccountUtils;
import br.net.silva.daniel.shared.application.value_object.Source;

//TODO: Precisa refatorar para receber um gateway
public class PasswordAndExistsAccountValidate implements IValidations {

    private final FindApplicationBaseGateway<AccountOutput> findAccountGataway;
    private final CreateAccountByAccountDTOFactory factory;

    public PasswordAndExistsAccountValidate(FindApplicationBaseGateway<AccountOutput> findAccountGataway) {
        this.findAccountGataway = findAccountGataway;
        this.factory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public void validate(Source input) throws GenericException {
        var findAccountParamDTO = (IAccountParam) input.input();
        AccountUtils.validatePassword(findAccountParamDTO.password());
        var accountOutput = findAccountGataway.findById(findAccountParamDTO);

        if (accountOutput.isEmpty()) {
            return;
        }

        var account = AccountBuilder.buildAggregate().createFrom(accountOutput.get());
        account.validatePassword(CryptoUtils.convertToSHA256(findAccountParamDTO.password()));
    }
}
