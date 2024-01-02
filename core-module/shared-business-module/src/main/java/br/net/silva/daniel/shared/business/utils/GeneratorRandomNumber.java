package br.net.silva.daniel.shared.business.utils;

import java.util.Random;

public class GeneratorRandomNumber {

    private GeneratorRandomNumber() {
        throw new IllegalStateException("Utility class");
    }

    private static final Random RANDOM = new Random();

    public static Integer generate(int max) {
        return generate(max, false);
    }

    public static Integer generate(int max, boolean generateWithZero) {
        Integer number;
        do {
            number = RANDOM.nextInt(max);
        } while (!generateWithZero && number.equals(0));

        return number;
    }
}
