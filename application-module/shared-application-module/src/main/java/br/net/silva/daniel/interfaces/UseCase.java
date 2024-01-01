package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;

public interface UseCase<R> {
    R exec(IGenericPort dto) throws GenericException;
}
