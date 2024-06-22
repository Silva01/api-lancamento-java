package br.net.silva.daniel.shared.application.interfaces;

public interface IMapperResponse<I, O> {
    boolean accept(Object input, O output);
    void toFillIn(I input, O output);
}
