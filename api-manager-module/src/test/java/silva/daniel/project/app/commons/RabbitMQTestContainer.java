package silva.daniel.project.app.commons;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public interface RabbitMQTestContainer {

    RabbitMQContainer RABBIT_MQ_CONTAINER = new RabbitMQContainer("rabbitmq:3");

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry){
        registry.add("spring.rabbitmq.host", RABBIT_MQ_CONTAINER::getHost);
        registry.add("spring.rabbitmq.port", RABBIT_MQ_CONTAINER::getAmqpPort);
        registry.add("spring.rabbitmq.username", RABBIT_MQ_CONTAINER::getAdminUsername);
        registry.add("spring.rabbitmq.password", RABBIT_MQ_CONTAINER::getAdminPassword);
    }
}
