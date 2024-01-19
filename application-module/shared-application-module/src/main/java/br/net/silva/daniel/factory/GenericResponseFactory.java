package br.net.silva.daniel.factory;

import br.net.silva.daniel.interfaces.IMapperResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
