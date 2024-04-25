package br.net.silva.daniel.shared.application.interfaces.spec;

import br.net.silva.daniel.shared.application.build.Builder;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;

public interface MapperSpec {
    <R> Builder<R> withGenericMapper(GenericResponseMapper mapper);
}
