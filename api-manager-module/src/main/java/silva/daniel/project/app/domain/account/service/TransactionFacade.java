package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.usecase.FindAccountByCpfUseCase;
import br.net.silva.business.usecase.FindAccountUseCase;
import br.net.silva.business.usecase.RegisterTransactionUseCase;
import br.net.silva.business.usecase.ReversalTransactionUseCase;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.build.FacadeBuilder;
import br.net.silva.daniel.shared.application.build.UseCaseBuilder;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.usecase.FindActiveClientUseCase;
import br.net.silva.daniel.value_object.output.ClientOutput;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TransactionFacade {

    private final GenericResponseMapper responseMapper;
    private final ApplicationBaseGateway<ClientOutput> clientBaseRepository;
    private final ApplicationBaseGateway<AccountOutput> accountBaseRepository;
    private final ApplicationBaseGateway<BatchTransactionInput> transactionBaseRepository;
    private final ApplicationBaseGateway<ReversalTransactionInput> reversalTransactionGateway;

    public GenericFacadeDelegate registerTransaction() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class)
                )
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, FindAccountUseCase.class)
                )
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(transactionBaseRepository, responseMapper, RegisterTransactionUseCase.class)
                )
                .build();
    }

    public GenericFacadeDelegate refundTransaction() {
        return FacadeBuilder
                .make()
                .withBuilderUseCase(
                        UseCaseBuilder.makeTo(clientBaseRepository, responseMapper, FindActiveClientUseCase.class)
                )
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(accountBaseRepository, responseMapper, FindAccountByCpfUseCase.class)
                )
                .andWithBuilderUseCase(
                        UseCaseBuilder.makeTo(reversalTransactionGateway, responseMapper, ReversalTransactionUseCase.class)
                )
                .build();
    }
}
