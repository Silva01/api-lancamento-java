package silva.daniel.project.app.domain.account.gateway;

import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.ITransactionParam;
import org.springframework.stereotype.Component;
import silva.daniel.project.app.domain.account.mapper.TransactionMapper;
import silva.daniel.project.app.domain.account.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;

@Component
public final class TransactionGateway implements ApplicationBaseGateway<TransactionOutput> {

    private final TransactionRepository repository;

    public TransactionGateway(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean deleteById(ParamGateway param) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    @Override
    public Optional<TransactionOutput> findById(ParamGateway param) {
        final var optTransaction = repository.findById(((ITransactionParam) param).id());
        return optTransaction.map(TransactionMapper.convert());
    }

    @Override
    public List<TransactionOutput> findAllBy(ParamGateway param) {
        return List.of();
    }

    @Override
    public TransactionOutput save(TransactionOutput entity) {
        return null;
    }

    @Override
    public void saveAll(List<TransactionOutput> paramList) {

    }
}
