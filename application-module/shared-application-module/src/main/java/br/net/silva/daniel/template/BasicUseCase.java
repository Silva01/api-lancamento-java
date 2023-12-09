package br.net.silva.daniel.template;

import br.net.silva.daniel.GenericException;

public abstract class BasicUseCase<D> {

    public abstract void exec(D dto) throws GenericException;
}
