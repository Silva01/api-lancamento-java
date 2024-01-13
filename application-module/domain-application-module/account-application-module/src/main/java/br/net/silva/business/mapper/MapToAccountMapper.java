package br.net.silva.business.mapper;

import br.net.silva.business.annotations.account.Number;
import br.net.silva.business.annotations.account.*;
import br.net.silva.business.annotations.creditcard.*;
import br.net.silva.business.utils.AccountMapperUtils;
import br.net.silva.business.utils.CreditCardMapperUtils;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.CreditCardDTO;
import br.net.silva.daniel.value_object.Source;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Map;

import static br.net.silva.business.enums.TypeAccountMapperEnum.ACCOUNT;
import static br.net.silva.business.enums.TypeAccountMapperEnum.CREDIT_CARD;

@Mapper(uses = {AccountMapperUtils.class, CreditCardMapperUtils.class})
public interface MapToAccountMapper {

    MapToAccountMapper MAPPER = Mappers.getMapper( MapToAccountMapper.class );

    @Mapping(source = "map", target = "number", qualifiedBy = Number.class)
    @Mapping(source = "map", target = "agency", qualifiedBy = Agency.class)
    @Mapping(source = "map", target = "balance", qualifiedBy = Balance.class)
    @Mapping(source = "map", target = "password", qualifiedBy = Password.class)
    @Mapping(source = "map", target = "active", qualifiedBy = Active.class)
    @Mapping(source = "map", target = "cpf", qualifiedBy = Cpf.class)
    AccountDTO mapToAccountDTO(Source source);

    @SuppressWarnings("unchecked")
    @AfterMapping
    default void mapCreditCardAndTransactions(@MappingTarget AccountDTO accountDTO, Source source) {
        Map<String, Object> mapAccount = (Map<String, Object>) source.map().get(ACCOUNT.name());
        if (mapAccount.containsKey(CREDIT_CARD.name())) {
            accountDTO.setCreditCard(mapToCreditCardDTO(source));
        }
    }

    @Mapping(source = "map", target = "number", qualifiedBy = CreditCardNumber.class)
    @Mapping(source = "map", target = "cvv", qualifiedBy = CreditCardCvv.class)
    @Mapping(source = "map", target = "active", qualifiedBy = CreditCardActive.class)
    @Mapping(source = "map", target = "balance", qualifiedBy = CreditCardBalance.class)
    @Mapping(source = "map", target = "expirationDate", qualifiedBy = CreditCardExpirationDate.class)
    @Mapping(source = "map", target = "flag", qualifiedBy = CreditCardFlag.class)
    CreditCardDTO mapToCreditCardDTO(Source source);
}
