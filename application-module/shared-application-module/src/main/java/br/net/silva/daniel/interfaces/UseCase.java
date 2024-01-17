package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.shared.business.value_object.Source;

public interface UseCase {
    void exec(Source param) throws GenericException;
}
