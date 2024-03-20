package silva.daniel.project.app.annotation;

import br.net.silva.business.mapper.CreateResponseToFindAccountsByCpfFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountByClientFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountFactory;
import br.net.silva.business.mapper.GetInformationMapper;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({
        CreateResponseToFindAccountsByCpfFactory.class,
        CreateResponseToNewAccountByClientFactory.class,
        CreateResponseToNewAccountFactory.class,
        GetInformationMapper.class
})
public @interface EnableMapper {
}
