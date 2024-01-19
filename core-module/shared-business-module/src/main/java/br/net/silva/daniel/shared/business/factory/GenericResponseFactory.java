package br.net.silva.daniel.shared.business.factory;

import br.net.silva.daniel.shared.business.interfaces.IMapperResponse;

import java.util.List;

public class GenericResponseFactory {

    private final List<IMapperResponse> mappers;

    public GenericResponseFactory(List<IMapperResponse> mappers) {
        this.mappers = mappers;
    }

    public void fillIn(Object input, Object output) {
        for(IMapperResponse mapper : mappers) {
            if (mapper.accept(input, output)) {
                mapper.toFillIn(input, output);
                break;
            }
        }
    }
}
