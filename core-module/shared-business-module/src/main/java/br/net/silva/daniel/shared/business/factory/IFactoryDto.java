package br.net.silva.daniel.shared.business.factory;

import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.interfaces.IProcessResponse;

public interface IFactoryDto<T extends IGenericPort> extends IProcessResponse<T> {
}
