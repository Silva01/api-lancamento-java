package silva.daniel.project.app.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import silva.daniel.project.app.validator.ConditionalCreditCardValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Repeatable(Conditionals.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConditionalCreditCardValidator.class)
public @interface ConditionalCreditCardValidation {
    String message() default "Object invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String selected();
    String[] required();
    String[] values();
}
