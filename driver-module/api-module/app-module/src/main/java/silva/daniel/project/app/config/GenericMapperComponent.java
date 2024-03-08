package silva.daniel.project.app.config;

import br.net.silva.business.mapper.CreateResponseToFindAccountsByCpfFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountByClientFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountFactory;
import br.net.silva.business.mapper.GetInformationMapper;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenericMapperComponent {

    @Bean
    public GenericResponseMapper createMapper() {
        return new GenericResponseMapper(List.of(
                new CreateResponseToFindAccountsByCpfFactory(),
                new CreateResponseToNewAccountByClientFactory(),
                new CreateResponseToNewAccountFactory(),
                new GetInformationMapper()
        ));
    }
}
