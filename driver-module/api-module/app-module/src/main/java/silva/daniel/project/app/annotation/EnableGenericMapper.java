package silva.daniel.project.app.annotation;

import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(GenericResponseMapper.class)
public @interface EnableGenericMapper {
}
