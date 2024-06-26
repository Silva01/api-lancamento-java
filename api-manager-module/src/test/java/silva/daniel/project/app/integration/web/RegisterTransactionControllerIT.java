package silva.daniel.project.app.integration.web;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import silva.daniel.project.app.commons.IntegrationAssertCommons;
import silva.daniel.project.app.commons.MysqlAndRabbitMQTestContainer;
import silva.daniel.project.app.commons.RequestIntegrationCommons;
import silva.daniel.project.app.domain.account.request.AccountTransactionRequest;
import silva.daniel.project.app.domain.account.request.TransactionBatchRequest;
import silva.daniel.project.app.domain.account.request.TransactionRequest;
import silva.daniel.project.app.domain.client.FailureResponse;

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
class RegisterTransactionControllerIT extends MysqlAndRabbitMQTestContainer implements IntegrationAssertCommons {

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

    @Test
    void registerTransaction_WithValidData_ReturnsStatus200() throws IOException {
        final var sourceAccount = new AccountTransactionRequest("12345678901", 1, 1234);
        final var destinyAccount = new AccountTransactionRequest("12345678910", 1,1237);
        final var transaction = new TransactionRequest("Test transaction", BigDecimal.valueOf(1000), 1, TransactionTypeEnum.DEBIT, 1234L, null, null);
        final var request = new TransactionBatchRequest(sourceAccount, destinyAccount, List.of(transaction));

        final var result = restTemplate.postForEntity(API_REGISTER_TRANSACTION, request, Void.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isEqualTo(1);

        GetResponse response = channel.basicGet(QUEUE_NAME, true);
        assertThat(response).isNotNull();

        final var sut = new String(response.getBody(), StandardCharsets.UTF_8);
        assertThat(sut).contains(sourceAccount.cpf());
        assertThat(sut).contains(destinyAccount.cpf());
        assertThat(sut).contains(transaction.idempotencyId().toString());
    }

    @ParameterizedTest
    @MethodSource("silva.daniel.project.app.commons.TransactionRequestBuilder#provideInvalidDataForRegisterTransaction")
    void registerTransaction_WithInvalidData_ReturnsStatus406(TransactionBatchRequest request) throws IOException {
        requestCommons.assertPostRequest(API_REGISTER_TRANSACTION, request, FailureResponse.class, this::assertInvalidData);
        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isZero();
    }

    @Test
    void registerTransaction_WithClientNotExists_ReturnsStatus404() throws IOException {
        final var request = new TransactionBatchRequest(new AccountTransactionRequest("12345678900", 1, 1234), new AccountTransactionRequest("12345678910", 1, 1237), List.of(new TransactionRequest("Test transaction", BigDecimal.valueOf(1000), 1, TransactionTypeEnum.DEBIT, 1234L, null, null)));
        requestCommons.assertPostRequest(API_REGISTER_TRANSACTION, request, FailureResponse.class, this::assertClientNotExists);
        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isZero();
    }

    @Test
    void registerTransaction_WithClientDeactivated_ReturnsStatus409() throws IOException {
        final var request = new TransactionBatchRequest(new AccountTransactionRequest("12345678903", 1, 1234), new AccountTransactionRequest("12345678910", 1, 1237), List.of(new TransactionRequest("Test transaction", BigDecimal.valueOf(1000), 1, TransactionTypeEnum.DEBIT, 1234L, null, null)));
        requestCommons.assertPostRequest(API_REGISTER_TRANSACTION, request, FailureResponse.class, this::assertClientDeactivatedExists);
        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isZero();
    }

    @Test
    void registerTransaction_WithAccountNotExists_ReturnsStatus404() throws IOException {
        final var request = new TransactionBatchRequest(new AccountTransactionRequest("12345678901", 1, 9999), new AccountTransactionRequest("12345678911", 1, 1237), List.of(new TransactionRequest("Test transaction", BigDecimal.valueOf(1000), 1, TransactionTypeEnum.DEBIT, 1234L, null, null)));
        requestCommons.assertPostRequest(API_REGISTER_TRANSACTION, request, FailureResponse.class, this::assertAccountNotExists);

        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isZero();
    }

    @Test
    void registerTransaction_WithAccountDeactivated_ReturnsStatus409() throws IOException {
        final var request = new TransactionBatchRequest(new AccountTransactionRequest("12345678904", 1, 1236), new AccountTransactionRequest("12345678910", 1, 1237), List.of(new TransactionRequest("Test transaction", BigDecimal.valueOf(1000), 1, TransactionTypeEnum.DEBIT, 1234L, null, null)));
        requestCommons.assertPostRequest(API_REGISTER_TRANSACTION, request, FailureResponse.class, this::assertAccountAlreadyDeactivated);

        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isZero();
    }

    @Test
    void registerTransaction_WithDuplicateTransaction_ReturnsStatus400() throws IOException {
        final var request = new TransactionBatchRequest(
                new AccountTransactionRequest("12345678901", 1, 1234),
                new AccountTransactionRequest("12345678910", 1, 1237),
                List.of(
                        new TransactionRequest("Test transaction", BigDecimal.valueOf(1000), 1, TransactionTypeEnum.DEBIT, 1234L, null, null),
                        new TransactionRequest("Test transaction", BigDecimal.valueOf(1000), 1, TransactionTypeEnum.DEBIT, 1234L, null, null)));

        requestCommons.assertPostRequest(API_REGISTER_TRANSACTION, request, FailureResponse.class, this::assertDuplicateTransaction);

        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isZero();
    }
}