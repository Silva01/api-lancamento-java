package br.net.silva.daniel.value_object.input;

import br.net.silva.daniel.interfaces.IClientParam;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

public record ActivateClient(
        String cpf
) implements IClientParam {
    @Override
    public String id() {
        throw GenericErrorUtils.executeException("Not Permission for this operation");
    }

    @Override
    public String name() {
        throw GenericErrorUtils.executeException("Not Permission for this operation");
    }

    @Override
    public String telephone() {
        throw GenericErrorUtils.executeException("Not Permission for this operation");
    }

    @Override
    public boolean active() {
        throw GenericErrorUtils.executeException("Not Permission for this operation");
    }

    @Override
    public AddressRequestDTO address() {
        throw GenericErrorUtils.executeException("Not Permission for this operation");
    }
}
