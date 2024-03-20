package silva.daniel.project.app.mapper;

import br.net.silva.daniel.shared.application.interfaces.Output;

public interface Mapper<O extends Output, E> {
    O mapTo(E entity);
    E mapTo(O output);
}
