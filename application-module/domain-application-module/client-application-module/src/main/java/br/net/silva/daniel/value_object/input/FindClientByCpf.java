package br.net.silva.daniel.value_object.input;

import br.net.silva.daniel.interfaces.IAddressParam;
import br.net.silva.daniel.interfaces.IClientParam;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

public record FindClientByCpf(
        String cpf
) implements IClientParam {
    @Override
    public String id() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    @Override
    public String name() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    @Override
    public String telephone() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    @Override
    public boolean active() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    @Override
    public Integer agency() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    @Override
    public IAddressParam address() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }
}
