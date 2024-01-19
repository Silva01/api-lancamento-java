package br.net.silva.daniel.interfaces;

public interface IMapperResponse<I, O> {
    boolean accept(I input, O output);
    void toFillIn(I input, O output);
}
