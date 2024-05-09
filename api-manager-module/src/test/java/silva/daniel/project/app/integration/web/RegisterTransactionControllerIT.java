package silva.daniel.project.app.integration.web;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import silva.daniel.project.app.commons.IntegrationAssertCommons;
import silva.daniel.project.app.commons.MysqlTestContainer;
import silva.daniel.project.app.commons.RabbitMQTestContainer;
import silva.daniel.project.app.commons.RequestIntegrationCommons;
import silva.daniel.project.app.domain.account.request.AccountTransactionRequest;
import silva.daniel.project.app.domain.account.request.TransactionBatchRequest;
import silva.daniel.project.app.domain.account.request.TransactionRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
final class RegisterTransactionControllerIT extends MysqlTestContainer implements IntegrationAssertCommons, RabbitMQTestContainer {

    private static final String API_REGISTER_TRANSACTION = "/api/transaction/register";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RequestIntegrationCommons requestCommons;

    private Channel channel;

    private static final String QUEUE_NAME = "transaction-queue";

    @BeforeEach
    void setUp() throws IOException, TimeoutException {
        final var factory = new ConnectionFactory();

        factory.setHost(RABBIT_MQ_CONTAINER.getHost());
        factory.setPort(RABBIT_MQ_CONTAINER.getAmqpPort());
        factory.setUsername(RABBIT_MQ_CONTAINER.getAdminUsername());
        factory.setPassword(RABBIT_MQ_CONTAINER.getAdminPassword());

        final var connection = factory.newConnection();
        this.channel = connection.createChannel();
    }

    @AfterEach
    void tearDown() throws IOException, TimeoutException {
        channel.close();
    }

    @Test
    void registerTransaction_WithValidData_ReturnsStatus200() throws IOException, TimeoutException, InterruptedException {
        final var sourceAccount = new AccountTransactionRequest("12345678901", 1, 1234);
        final var destinyAccount = new AccountTransactionRequest("12345678910", 1,1237);
        final var transaction = new TransactionRequest("Test transaction", BigDecimal.valueOf(1000), 1, TransactionTypeEnum.DEBIT, 1234L, null, null);
        final var request = new TransactionBatchRequest(sourceAccount, destinyAccount, List.of(transaction));

        final var result = restTemplate.postForEntity(API_REGISTER_TRANSACTION, request, Void.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isEqualTo(1);

        GetResponse response = channel.basicGet(QUEUE_NAME, false);
        assertThat(response).isNotNull();

        final var sut = new String(response.getBody(), StandardCharsets.UTF_8);
        assertThat(sut).isNotEmpty();
        assertThat(sut).contains("12345678901");
    }
}