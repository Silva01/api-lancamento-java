package br.net.silva.daniel.integrations.test.interfaces;

import java.util.Random;

public abstract class GeneratorRandomValues {

    private final Random random = new Random();

    protected Long generateRandomIdTransaction() {
        return generateRandomNumberLong(100);
    }

    protected Integer generateRandomAccountNumber() {
        return generateRandomNumber(1000) + 1;
    }

    protected Long generateIdempotencyId() {
        return generateRandomNumberLong(1000000);
    }

    protected Integer generateRandomNumber(int bound) {
        return random.nextInt(bound);
    }

    protected Integer generateRandomNumberLogic() {
        return random.nextInt(2);
    }

    protected Long generateRandomNumberLong(int bound) {
        return random.nextLong(bound) + 1;
    }
}
