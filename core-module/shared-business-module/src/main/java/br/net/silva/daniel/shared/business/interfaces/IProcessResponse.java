package br.net.silva.daniel.shared.business.interfaces;

public interface IProcessResponse<T extends IGenericPort> {
    T build();
}
