package br.net.silva.business.mapper;

import br.net.silva.business.dto.FindAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper
public interface MapToFindAccountMapper {

    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "agency", source = "agency")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "password", source = "password")
    FindAccountDTO mapToFindAccountDto(Map<String, String> map);
}
