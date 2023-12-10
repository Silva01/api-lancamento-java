package br.net.silva.daniel.template;

import br.net.silva.daniel.exception.GenericException;

public abstract class UseCase<D> {

    public abstract void exec(D dto) throws GenericException;
}
