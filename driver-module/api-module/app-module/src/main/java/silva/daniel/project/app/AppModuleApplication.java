package silva.daniel.project.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({
        @ComponentScan("br.net.silva.daniel")
})
public class AppModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppModuleApplication.class, args);
    }

}
