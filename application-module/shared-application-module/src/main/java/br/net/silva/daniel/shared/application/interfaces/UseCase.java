package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.application.annotations.ValidateOn;
import br.net.silva.daniel.shared.application.exception.BuildValidationException;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Optional;

public interface UseCase<R> {
    R exec(Source param) throws GenericException;

    @SuppressWarnings("unchecked")
    default Extractor<R> execValidate(Optional<R> opt) throws GenericException {
        var validationAnnotation = this.getClass().getAnnotation(ValidateOn.class);
        if (validationAnnotation != null) {
            for (var validationClass : validationAnnotation.validations()) {
                try {
                    var validation = (Validation<R>) validationClass.getDeclaredConstructor().newInstance();
                    validation.validate(opt);
                    return () -> opt.orElse(null);
                } catch (GenericException e) {
                    throw e;
                } catch (Exception e) {
                    throw new BuildValidationException(e);
                }
            }
        }
        return () -> opt.orElse(null);
    }

    interface Extractor<R> {
        R extract();
    }
}
