package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.application.gateway.ParamGateway;

public interface ICpfParam extends Input, ParamGateway {
    String cpf();
}
