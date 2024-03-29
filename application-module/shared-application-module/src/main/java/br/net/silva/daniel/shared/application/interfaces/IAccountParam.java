package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.application.gateway.ParamGateway;

import java.math.BigDecimal;

public interface IAccountParam extends Input, ICpfParam, IAgencyParam, IAccountNumberParam, ParamGateway {
    BigDecimal balance();
    String password();
    boolean active();
}
