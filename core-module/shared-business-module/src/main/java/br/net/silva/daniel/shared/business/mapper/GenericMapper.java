package br.net.silva.daniel.shared.business.mapper;

import br.net.silva.daniel.shared.business.exception.MapperNotConvertErrorException;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.interfaces.IMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class GenericMapper<T> implements IMapper<T, IGenericPort> {

    private final Class<T> clazz;

    public GenericMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T map(IGenericPort param) {
        return toMap(param.get(), this.clazz);
    }

    @SuppressWarnings("unchecked")
    private T toMap(Object object, Class<T> clazz) {
        try {
            Class<T> targetClass = (Class<T>) object.getClass();
            // Find the constructor of the targetClass with the appropriate parameters
            Constructor<T> constructor = targetClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            T dto = constructor.newInstance();

            for (Field targetField : targetClass.getDeclaredFields()) {
                Field sourceField = clazz.getDeclaredField(targetField.getName());
                sourceField.setAccessible(true);
                targetField.setAccessible(true);

                Object value = sourceField.get(object);
                targetField.set(dto, value);
            }

            return dto;
        } catch (Exception e) {
            throw new MapperNotConvertErrorException(e);
        }
    }
}
