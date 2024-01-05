package br.net.silva.daniel.shared.business.mapper;

import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.interfaces.IMapper;

import java.lang.reflect.Field;
import java.util.Arrays;

public class GenericMapper<T> implements IMapper<T, IGenericPort> {

    private final Class<T> clazz;

    public GenericMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T map(IGenericPort param) {
        return convert(param.get(), this.clazz);
    }

    public static <T, U> U convert(T source, Class<U> targetClass) {
        try {
            U target = targetClass.getDeclaredConstructor().newInstance();

            Field[] sourceFields = source.getClass().getDeclaredFields();
            Field[] targetFields = targetClass.getDeclaredFields();

            for (Field targetField : targetFields) {
                Field sourceField = findMatchingField(targetField, sourceFields);
                if (sourceField != null) {
                    sourceField.setAccessible(true);
                    targetField.setAccessible(true);
                    Object value = sourceField.get(source);
                    targetField.set(target, value);
                }
            }

            return target;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao mapear records", e);
        }
    }

    private static Field findMatchingField(Field targetField, Field[] sourceFields) {
        return Arrays.stream(sourceFields)
                .filter(field -> field.getName().equals(targetField.getName()))
                .findFirst()
                .orElse(null);
    }
}
