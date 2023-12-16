package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;

public interface UseCase<D, R> {
    R exec(D dto) throws GenericException;
}
