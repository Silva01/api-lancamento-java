package br.net.silva.business.utils;

import br.net.silva.business.annotations.transaction.*;
import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.utils.ExtractMapUtils;

import java.math.BigDecimal;
import java.util.Map;

public class TransactionMapperUtils {

    @TransactionId
    public Long id(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "id", Long.class);
    }

    @TransactionDescription
    public String description(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "description", String.class);
    }

    @TransactionPrice
    public BigDecimal price(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "price", BigDecimal.class);
    }

    @TransactionQuantity
    public Integer quantity(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "quantity", Integer.class);
    }

    @TransactionType
    public TransactionTypeEnum type(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "type", TransactionTypeEnum.class);
    }

    @TransactionOriginAccountNumber
    public Integer originAccountNumber(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "originAccountNumber", Integer.class);
    }

    @TransactionDestinationAccountNumber
    public Integer destinationAccountNumber(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "destinationAccountNumber", Integer.class);
    }

    @TransactionIdempotencyId
    public Long idempotencyId(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "idempotencyId", Long.class);
    }

    @TransactionCreditCardNumber
    public String creditCardNumber(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "creditCardNumber", String.class);
    }

    @TransactionCreditCardCvv
    public Integer creditCardCvv(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, "creditCardCvv", Integer.class);
    }
}
