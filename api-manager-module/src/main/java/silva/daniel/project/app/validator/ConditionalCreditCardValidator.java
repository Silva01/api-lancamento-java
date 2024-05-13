package silva.daniel.project.app.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import silva.daniel.project.app.annotation.ConditionalCreditCardValidation;

import java.util.Arrays;

import static org.springframework.util.ObjectUtils.isEmpty;

public class ConditionalCreditCardValidator implements ConstraintValidator<ConditionalCreditCardValidation, Object> {

    private String selected;
    private String[] required;
    private String message;
    private String[] values;

    @Override
    public void initialize(ConditionalCreditCardValidation constraintAnnotation) {
        selected = constraintAnnotation.selected();
        required = constraintAnnotation.required();
        message = constraintAnnotation.message();
        values = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean valid = true;
        try {
            Object checkedValue = extractValue(object, selected);
            if (Arrays.asList(values).contains(checkedValue.toString())) {
                for (String propName : required) {
                    Object requiredValue = extractValue(object, propName);
                    valid = valueValidation(requiredValue);
                    if (!valid) {
                        context.disableDefaultConstraintViolation();
                        context.buildConstraintViolationWithTemplate(message).addPropertyNode(propName).addConstraintViolation();
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return valid;
    }

    private static Object extractValue(Object object, String propName) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> field.getName().equals(propName))
                .findFirst()
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(object);
                    } catch (IllegalAccessException e) {
                        return null;
                    }
                }).orElse(null);
    }

    private static boolean valueValidation(Object value) {
        return nonNullValueValidation(value) && (stringValueValidation(value) || integerValueValidation(value));
    }

    private static boolean stringValueValidation(Object value) {
        return value instanceof String && !isEmpty(value);
    }

    private static boolean integerValueValidation(Object value) {
        return value instanceof Integer result && result > 0;
    }

    private static boolean nonNullValueValidation(Object value) {
        return value != null;
    }
}
