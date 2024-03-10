package silva.daniel.project.app.config;

import br.net.silva.business.mapper.CreateResponseToFindAccountsByCpfFactory;
import br.net.silva.business.mapper.CreateResponseToNewAccountByClientFactory;
import br.net.silva.business.mapper.GetInformationMapper;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenericMapperConfig {

    @Bean
    public GenericResponseMapper getMapper() {
        return new GenericResponseMapper(
                List.of(
                        new CreateResponseToFindAccountsByCpfFactory(),
                        new CreateResponseToNewAccountByClientFactory(),
                        new CreateResponseToFindAccountsByCpfFactory(),
                        new GetInformationMapper()
                )
        );
    }
}
