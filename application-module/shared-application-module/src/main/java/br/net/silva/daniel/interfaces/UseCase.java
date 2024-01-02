package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;

public interface UseCase<R> {
    R exec(IGenericPort dto) throws GenericException;
}
