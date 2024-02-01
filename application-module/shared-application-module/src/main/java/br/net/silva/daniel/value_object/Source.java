package br.net.silva.daniel.value_object;

import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.Input;
import br.net.silva.daniel.interfaces.Output;

public record Source(
    Output output,
    Input input
) {
    public Source(Input input) {
        this(EmptyOutput.INSTANCE, input);
    }
}
