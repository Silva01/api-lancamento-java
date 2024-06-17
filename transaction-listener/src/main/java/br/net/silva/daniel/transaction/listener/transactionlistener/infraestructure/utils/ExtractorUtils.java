package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.utils;

import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.IExtractor;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.aggregate.BaseAccountAggregate;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ExtractorUtils {

    public static Account accountExtractor(BaseAccountAggregate baseAccountAggregate) {
        return extractor(baseAccountAggregate.accountConfigValidation().accountExtractor(), Account.class);
    }

    public static <T> T extractor(IExtractor extractor, Class<T> clazz) {
        return clazz.cast(extractor.extract());
    }
}
