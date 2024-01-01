package br.net.silva.daniel.interfaces;

public interface IMapper<R, P> {
    R map(P param);
}
