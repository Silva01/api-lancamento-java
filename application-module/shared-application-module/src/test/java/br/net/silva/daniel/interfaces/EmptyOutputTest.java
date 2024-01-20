package br.net.silva.daniel.interfaces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptyOutputTest {

    @Test
    void shouldInstanceEmptyOutput() {
        assertNotNull(EmptyOutput.INSTANCE);
    }

}