package br.net.silva.daniel.mapper;

public interface IMapper<R, P> {
    R map(P param);
}
