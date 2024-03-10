package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.application.exception.GenericException;

public interface IFacade<D, R> {
    R execute(D dto) throws GenericException;
}
