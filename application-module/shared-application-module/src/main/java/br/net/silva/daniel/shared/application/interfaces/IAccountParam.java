package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.math.BigDecimal;

public interface IAccountParam extends Input, ICpfParam, IAgencyParam, IAccountNumberParam, ParamGateway {
    default BigDecimal balance() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    default String password() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }

    default boolean active() {
        throw GenericErrorUtils.executeExceptionNotPermissionOperation();
    }
}
