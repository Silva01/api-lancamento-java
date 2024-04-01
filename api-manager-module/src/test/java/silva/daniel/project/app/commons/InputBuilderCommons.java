package silva.daniel.project.app.commons;

import br.net.silva.business.value_object.input.CreateCreditCardInput;

public interface InputBuilderCommons {

    default CreateCreditCardInput buildNewBaseCreditCardInput() {
        return new CreateCreditCardInput("33344455566", 123, 1);
    }
}
