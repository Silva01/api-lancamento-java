package br.net.silva.business.mapper;

import br.net.silva.business.value_object.input.FindAccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface MapToFindAccountMapper {

    MapToFindAccountMapper INSTANCE = Mappers.getMapper(MapToFindAccountMapper.class);

    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "agency", source = "agency")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "password", source = "password")
    FindAccountDTO mapToFindAccountDto(Map<String, String> map);
}
