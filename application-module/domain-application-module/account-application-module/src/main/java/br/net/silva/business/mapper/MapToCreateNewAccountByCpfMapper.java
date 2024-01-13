package br.net.silva.business.mapper;

import br.net.silva.business.dto.CreateNewAccountByCpfDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper
public interface MapToCreateNewAccountByCpfMapper {

    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "agency", source = "agency")
    @Mapping(target = "password", source = "password")
    CreateNewAccountByCpfDTO mapToCreateNewAccountByCpfDto(Map<String, String> map);
}
