package br.net.silva.business.interfaces;

import java.util.Random;

public abstract class GeneratorRandomValues {

    private final Random random = new Random();

    protected Long generateRandomIdTransaction() {
        return generateRandomNumberLong(100);
    }

    protected Integer generateRandomAccountNumber() {
        return generateRandomNumber(1000);
    }

    protected Long generateIdempotencyId() {
        return generateRandomNumberLong(1000000);
    }

    protected Integer generateRandomNumber(int bound) {
        return random.nextInt(bound) + 1;
    }

    protected Long generateRandomNumberLong(int bound) {
        return random.nextLong(bound) + 1;
    }
}
