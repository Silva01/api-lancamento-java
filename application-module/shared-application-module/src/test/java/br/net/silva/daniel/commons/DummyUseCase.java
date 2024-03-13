package br.net.silva.daniel.commons;

import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseRepository;
import br.net.silva.daniel.shared.application.value_object.Source;

public class DummyUseCase implements UseCase<String> {

    public DummyUseCase(ApplicationBaseRepository<String> repository) {
    }
    @Override
    public String exec(Source param) throws GenericException {
        return "Simulation Use Case";
    }
}
