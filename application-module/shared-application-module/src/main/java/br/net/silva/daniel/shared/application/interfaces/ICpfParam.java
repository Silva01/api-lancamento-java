package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.application.repository.ParamRepository;

public interface ICpfParam extends Input, ParamRepository {
    String cpf();
}
