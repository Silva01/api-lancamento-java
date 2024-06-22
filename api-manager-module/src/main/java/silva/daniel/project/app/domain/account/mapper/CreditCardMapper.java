package silva.daniel.project.app.domain.account.mapper;

import br.net.silva.business.value_object.output.CreditCardOutput;
import silva.daniel.project.app.domain.account.entity.CreditCard;

public interface CreditCardMapper {
    static CreditCardOutput toOutput(CreditCard entity) {

        if (entity == null) {
            return null;

        }

        return new CreditCardOutput(
                entity.getNumber(),
                entity.getCvv(),
                entity.getFlag(),
                entity.getBalance(),
                entity.getExpirationDate(),
                entity.isActive()
        );
    }
}
