package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.commons;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.RabbitMQContainer;

@TestConfiguration(proxyBeanMethods = false)
public class RabbitMQTestContainer {

    @ServiceConnection
    protected static final RabbitMQContainer RABBIT_MQ_CONTAINER = new RabbitMQContainer("rabbitmq:3");

    static {
        RABBIT_MQ_CONTAINER.start();
    }
}
