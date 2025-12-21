package br.net.silva.daniel.commons;

import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class DummyUseCase implements UseCase<String> {

    public DummyUseCase(ApplicationBaseGateway<String> repository) {
    }

    @Override
    public String exec(Source param) throws DomainGenericException {
        return "Simulation Use Case";
    }
}
