package br.net.silva.business.value_object.output;

import br.net.silva.business.enums.AccountStatusEnum;
import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.shared.application.interfaces.Output;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GetInformationAccountOutput implements Output {
    private Integer agency;
    private Integer accountNumber;
    private String status;
    private BigDecimal balance;
    private boolean isHaveCreditCard;
    private List<TransactionDTO> transactions;
}
