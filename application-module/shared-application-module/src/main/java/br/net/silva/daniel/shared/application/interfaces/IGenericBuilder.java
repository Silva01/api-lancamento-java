package br.net.silva.daniel.shared.application.interfaces;

@FunctionalInterface
public interface IGenericBuilder<R, P> {
    R createFrom(P param);
}
