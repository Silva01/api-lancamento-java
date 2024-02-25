package br.net.silva.business.build;

@FunctionalInterface
public interface IGenericBuilder<R, P> {
    R createFrom(P param);
}
