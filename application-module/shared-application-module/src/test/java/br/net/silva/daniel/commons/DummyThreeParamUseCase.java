package br.net.silva.daniel.commons;

import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

public class DummyThreeParamUseCase implements UseCase<String> {

    public DummyThreeParamUseCase(ApplicationBaseGateway<String> repository, GenericResponseMapper mapper, String name) {
    }

    @Override
    public String exec(Source param) throws GenericException {
        return "Simulation Use Case";
    }
}
