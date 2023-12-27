package br.net.silva.daniel.factory;

import br.net.silva.daniel.interfaces.IGenericPort;
import br.net.silva.daniel.interfaces.IProcessResponse;

public interface IFactoryDto<T extends IGenericPort> extends IProcessResponse<T> {
}
