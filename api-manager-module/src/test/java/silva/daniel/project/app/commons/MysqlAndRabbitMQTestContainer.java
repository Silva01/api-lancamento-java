package silva.daniel.project.app.commons;

public abstract class MysqlAndRabbitMQTestContainer extends MysqlTestContainer implements RabbitMQTestContainer {

    static {
        RABBIT_MQ_CONTAINER.start();
    }
}
