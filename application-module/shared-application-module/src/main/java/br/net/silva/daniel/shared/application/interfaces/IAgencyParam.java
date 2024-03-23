package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.application.gateway.ParamGateway;

public interface IAgencyParam extends Input, ParamGateway {
    Integer agency();
}
