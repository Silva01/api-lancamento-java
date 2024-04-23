package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.decorator.ValidationDecorator;
import br.net.silva.daniel.shared.application.exception.BuildValidationException;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

public interface UseCase<R> {
    R exec(Source param) throws GenericException;

    default Extractor<R> execValidate(Optional<R> opt, ValidationDecorator<R> decorator) throws GenericException {
        final var response = execValidate(opt);
        decorator.decorator(opt.orElse(null));
        return response;
    }

    @SuppressWarnings("unchecked")
    default Extractor<R> execValidate(Optional<R> opt) throws GenericException {
        var validationAnnotation = this.getClass().getAnnotation(ValidateStrategyOn.class);
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
