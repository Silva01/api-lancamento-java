package br.net.silva.daniel.shared.application.value_object;

import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.Input;
import br.net.silva.daniel.shared.application.interfaces.Output;

public record Source(
    Output output,
    Input input
) {
    public Source(Input input) {
        this(EmptyOutput.INSTANCE, input);
    }

    public static Source of(Input input) {
        return new Source(input);
    }
}
