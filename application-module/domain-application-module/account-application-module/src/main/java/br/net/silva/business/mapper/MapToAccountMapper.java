package br.net.silva.business.mapper;

import br.net.silva.business.annotations.account.Number;
import br.net.silva.business.annotations.account.*;
import br.net.silva.business.annotations.creditcard.*;
import br.net.silva.business.annotations.transaction.*;
import br.net.silva.business.utils.AccountMapperUtils;
import br.net.silva.business.utils.CreditCardMapperUtils;
import br.net.silva.business.utils.TransactionMapperUtils;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.CreditCardDTO;
import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.value_object.Source;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

import static br.net.silva.business.enums.TypeAccountMapperEnum.*;

@Mapper(uses = {AccountMapperUtils.class, CreditCardMapperUtils.class, TransactionMapperUtils.class})
public interface MapToAccountMapper {

    MapToAccountMapper INSTANCE = Mappers.getMapper(MapToAccountMapper.class);

    @Mapping(source = "map", target = "number", qualifiedBy = Number.class)
    @Mapping(source = "map", target = "agency", qualifiedBy = Agency.class)
    @Mapping(source = "map", target = "balance", qualifiedBy = Balance.class)
    @Mapping(source = "map", target = "password", qualifiedBy = Password.class)
    @Mapping(source = "map", target = "active", qualifiedBy = Active.class)
    @Mapping(source = "map", target = "cpf", qualifiedBy = Cpf.class)
    @Mapping(target = "creditCard", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    AccountDTO mapToAccountDTO(Source source);

    @SuppressWarnings("unchecked")
    @AfterMapping
    default void mapCreditCardAndTransactions(@MappingTarget AccountDTO accountDTO, Source source) {
        Map<String, Object> mapAccount = (Map<String, Object>) source.map().get(ACCOUNT.name());
        if (mapAccount.containsKey(CREDIT_CARD.name())) {
            accountDTO.setCreditCard(mapToCreditCardDTO(new Source((Map<String, Object>) mapAccount.get(CREDIT_CARD.name()))));
        }

        if(mapAccount.containsKey(TRANSACTION.name())) {
            List<Map<String, Object>> transactionsMap = (List<Map<String, Object>>) mapAccount.get(TRANSACTION.name());
            accountDTO.setTransactions(transactionsMap.stream().map(transactionMap -> mapToTransactionDTO(new Source(transactionMap))).toList());
        }
    }

    @Mapping(source = "map", target = "number", qualifiedBy = CreditCardNumber.class)
    @Mapping(source = "map", target = "cvv", qualifiedBy = CreditCardCvv.class)
    @Mapping(source = "map", target = "active", qualifiedBy = CreditCardActive.class)
    @Mapping(source = "map", target = "balance", qualifiedBy = CreditCardBalance.class)
    @Mapping(source = "map", target = "expirationDate", qualifiedBy = CreditCardExpirationDate.class)
    @Mapping(source = "map", target = "flag", qualifiedBy = CreditCardFlag.class)
    CreditCardDTO mapToCreditCardDTO(Source source);

    @Mapping(source = "map", target = "id", qualifiedBy = TransactionId.class)
    @Mapping(source = "map", target = "description", qualifiedBy = TransactionDescription.class)
    @Mapping(source = "map", target = "price", qualifiedBy = TransactionPrice.class)
    @Mapping(source = "map", target = "quantity", qualifiedBy = TransactionQuantity.class)
    @Mapping(source = "map", target = "type", qualifiedBy = TransactionType.class)
    @Mapping(source = "map", target = "originAccountNumber", qualifiedBy = TransactionOriginAccountNumber.class)
    @Mapping(source = "map", target = "destinationAccountNumber", qualifiedBy = TransactionDestinationAccountNumber.class)
    @Mapping(source = "map", target = "idempotencyId", qualifiedBy = TransactionIdempotencyId.class)
    @Mapping(source = "map", target = "creditCardNumber", qualifiedBy = TransactionCreditCardNumber.class)
    @Mapping(source = "map", target = "creditCardCvv", qualifiedBy = TransactionCreditCardCvv.class)
    TransactionDTO mapToTransactionDTO(Source source);
}
