package br.net.silva.daniel.shared.application.interfaces.spec;

import br.net.silva.daniel.shared.application.build.Builder;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;

public interface MapperSpec {
    Builder<UseCase<?>> withGenericMapper(GenericResponseMapper mapper);
}
