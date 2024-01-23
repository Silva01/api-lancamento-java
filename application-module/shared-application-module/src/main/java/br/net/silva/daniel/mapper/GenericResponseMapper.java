package br.net.silva.daniel.mapper;

import br.net.silva.daniel.interfaces.IMapperResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
