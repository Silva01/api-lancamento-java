package silva.daniel.project.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import silva.daniel.project.app.annotation.EnableGenericMapper;

@SpringBootApplication
@EnableGenericMapper
public class AppModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppModuleApplication.class, args);
    }

}
