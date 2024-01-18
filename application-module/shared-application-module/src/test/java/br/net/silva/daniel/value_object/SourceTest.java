package br.net.silva.daniel.value_object;

import br.net.silva.daniel.shared.business.interfaces.IGenericOutput;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SourceTest {

    @Test
    void shouldCreateInputSource() {
        Source source = new Source();
        source.input().put("key", "value");
        assertEquals("value", source.input().get("key"));
    }

    @Test
    void testShouldCreateMapSource() {
        Source source = new Source();
        source.map().put("key", new GenericOutput());
        assertEquals("Test", ((GenericOutput) source.map().get("key")).getTest());
    }

    private class GenericOutput implements IGenericOutput {
        private String test = "Test";

        public String getTest() {
            return test;
        }
    }

}