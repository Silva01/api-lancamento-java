package br.net.silva.daniel.interfaces;

public interface IMapperResponse<I, O> {
    boolean accept(Object input, O output);
    void toFillIn(I input, O output);
}
