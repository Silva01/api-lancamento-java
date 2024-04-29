package silva.daniel.project.app.commons;

import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.input.CreateCreditCardInput;

public interface InputBuilderCommons {

    default CreateCreditCardInput buildNewBaseCreditCardInput() {
        return new CreateCreditCardInput("33344455566", 123, 1);
    }

    default ChangeAgencyInput buildNewBaseChangeAgencyInput() {
        return new ChangeAgencyInput("12344455566", 1234, 1234, 1234);
    }
}
