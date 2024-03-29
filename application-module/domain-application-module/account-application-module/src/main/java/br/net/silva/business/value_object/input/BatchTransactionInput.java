package br.net.silva.business.value_object.input;

import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.math.BigDecimal;
import java.util.List;

public record BatchTransactionInput(
        AccountInput sourceAccount,
        AccountInput destinyAccount,
        List<TransactionInput> batchTransaction
) implements IAccountParam {
    @Override
    public Integer accountNumber() {
        return sourceAccount().accountNumber();
    }

    @Override
    public BigDecimal balance() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    @Override
    public String password() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    @Override
    public boolean active() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    @Override
    public Integer agency() {
        return sourceAccount().agency();
    }

    @Override
    public String cpf() {
        return sourceAccount().cpf();
    }
}
