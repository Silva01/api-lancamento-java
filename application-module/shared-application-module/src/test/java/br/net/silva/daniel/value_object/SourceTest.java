package br.net.silva.daniel.value_object;

import br.net.silva.daniel.shared.application.interfaces.Input;
import br.net.silva.daniel.shared.application.interfaces.Output;
import br.net.silva.daniel.shared.application.value_object.Source;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SourceTest {

    @Test
    void shouldCreateAndPrintOutputValue() {
        Source source = new Source(new GenericOutput(), new GenericInput());
        ((GenericOutput) source.output()).setTest("Value");
        assertEquals("Value", ((GenericOutput) source.output()).getTest());
    }

    @Test
    void testShouldCreateAndPrintInputValue() {
        Source source = new Source(new GenericOutput(), new GenericInput());
        assertEquals("Test Input", ((GenericInput) source.input()).getTest());
    }

    private class GenericInput implements Input {
        private String test = "Test Input";

        public String getTest() {
            return test;
        }
    }

    private class GenericOutput implements Output {
        private String test = "Test Output";

        public String getTest() {
            return test;
        }

        public void setTest(String test) {
            this.test = test;
        }
    }

}