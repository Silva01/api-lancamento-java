package br.net.silva.daniel.shared.business.value_object;

import br.net.silva.daniel.shared.business.interfaces.IGenericOutput;
import junit.framework.TestCase;

public class SourceTest extends TestCase {

    public void testShouldCreateInputSource() {
        Source source = new Source();
        source.input().put("key", "value");
        assertEquals("value", source.input().get("key"));
    }

    public void testShouldCreateMapSource() {
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