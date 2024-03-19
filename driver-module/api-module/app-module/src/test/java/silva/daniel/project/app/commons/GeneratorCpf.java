package silva.daniel.project.app.commons;

import java.util.Random;

public interface GeneratorCpf {

    Random RANDOM = new Random();

    static String start() {
        return String.valueOf(RANDOM.nextLong(99999999999L));
    }
}
