package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

public interface ICpfParam extends Input, ParamGateway {
    default String cpf() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }
}
