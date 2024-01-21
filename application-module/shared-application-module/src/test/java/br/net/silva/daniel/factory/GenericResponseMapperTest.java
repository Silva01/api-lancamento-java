package br.net.silva.daniel.factory;

import br.net.silva.daniel.interfaces.IMapperResponse;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenericResponseMapperTest {

    private GenericResponseMapper factory;

    @Test
    void shouldMapperStringToStringBuilderWithSuccess() {
        StringBuilder output = new StringBuilder();
        String input = "1";

        factory = new GenericResponseMapper(List.of(new StringMapper()));
        factory.fillIn(input, output);
        assertEquals("1", output.toString());
    }

    @Test
    void shouldMapperStringToStringEmptyWithTypeDifferent() {
        Integer output = 23;
        String input = "1";

        factory = new GenericResponseMapper(List.of(new StringMapper()));
        factory.fillIn(input, output);
        assertEquals(23, output);
    }

    private class StringMapper implements IMapperResponse<String, Object> {

        @Override
        public boolean accept(Object input, Object output) {
            return input instanceof String && output instanceof StringBuilder;
        }

        @Override
        public void toFillIn(String input, Object output) {
            ((StringBuilder) output).append(input);
        }
    }
}