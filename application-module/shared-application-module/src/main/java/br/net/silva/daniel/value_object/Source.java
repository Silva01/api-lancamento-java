package br.net.silva.daniel.value_object;

import br.net.silva.daniel.shared.business.interfaces.IGenericOutput;

import java.util.HashMap;
import java.util.Map;

public record Source(
        Map<String, IGenericOutput> map,
        Map<String, String> input
) {
    public Source(Map<String, IGenericOutput> map) {
        this(map, new HashMap<>());
    }
    public Source() {
        this(new HashMap<>(), new HashMap<>());
    }
}
