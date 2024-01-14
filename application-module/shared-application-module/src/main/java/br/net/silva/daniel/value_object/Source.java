package br.net.silva.daniel.value_object;

import java.util.HashMap;
import java.util.Map;

public record Source(
        Map<String, Object> map,
        Map<String, String> input
) {
    public Source(Map<String, Object> map) {
        this(map, new HashMap<>());
    }
    public Source() {
        this(new HashMap<>(), new HashMap<>());
    }
}
