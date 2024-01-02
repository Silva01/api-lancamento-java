package br.net.silva.daniel.shared.business.interfaces;

public interface IMapper<R, P> {
    R map(P param);
}
