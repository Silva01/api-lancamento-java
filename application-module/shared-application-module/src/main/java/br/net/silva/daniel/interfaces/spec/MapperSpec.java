package br.net.silva.daniel.interfaces.spec;

import br.net.silva.daniel.build.Builder;
import br.net.silva.daniel.mapper.GenericResponseMapper;

public interface MapperSpec {
    <T> Builder<T> withGenericMapper(GenericResponseMapper mapper);
}
