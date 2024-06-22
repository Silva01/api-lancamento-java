package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.commons;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class MysqlTestContainer {

    @ServiceConnection
    protected static final MySQLContainer MY_SQL_CONTAINER = new MySQLContainer("mysql:8.0.26")
            .withDatabaseName("finance")
            .withUsername("root")
            .withPassword("123456");

    static {
        MY_SQL_CONTAINER.start();
    }
}
