package br.net.silva.daniel.utils;

import junit.framework.TestCase;

public class GeneratorRandomNumberTest extends TestCase {

    public void testShouldGenerateNumberMoreThenZero() {
        var number = GeneratorRandomNumber.generate(10);
        assertNotNull(number);
        assertFalse(number < 0);
        assertFalse(number.equals(0));
    }
}