package br.net.silva.daniel.shared.application.interfaces;

import org.springframework.stereotype.Component;

@Component
public interface IMapperResponse<I, O> {
    boolean accept(Object input, O output);
    void toFillIn(I input, O output);
}
