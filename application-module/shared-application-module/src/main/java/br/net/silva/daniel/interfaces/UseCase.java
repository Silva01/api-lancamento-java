package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.value_object.Source;

public interface UseCase<R> {
    R exec(Source param) throws GenericException;
}
