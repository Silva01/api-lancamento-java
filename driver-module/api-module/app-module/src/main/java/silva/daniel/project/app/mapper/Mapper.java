package silva.daniel.project.app.mapper;

import br.net.silva.daniel.interfaces.Output;

public interface Mapper<O extends Output, E> {
    boolean accept(Class<O> output, Class<E> entity);
    O mapTo(E entity);
    E mapTo(O output);
}
