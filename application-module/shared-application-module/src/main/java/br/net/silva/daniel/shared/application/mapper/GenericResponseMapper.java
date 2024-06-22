package br.net.silva.daniel.shared.application.mapper;

import br.net.silva.daniel.shared.application.interfaces.IMapperResponse;

import java.util.List;

public class GenericResponseMapper {

    private final List<IMapperResponse> mappers;

    public GenericResponseMapper(List<IMapperResponse> mappers) {
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
