package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;

public interface IFacade<D, R> {
    R execute(D dto) throws GenericException;
}
