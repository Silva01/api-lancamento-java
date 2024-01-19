package br.net.silva.daniel.shared.business.interfaces;

public interface IMapperResponse<I, O> {
    boolean accept(I input, O output);
    void toFillIn(I input, O output);
}
