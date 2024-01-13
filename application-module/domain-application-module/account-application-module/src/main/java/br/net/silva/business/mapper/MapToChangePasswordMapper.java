package br.net.silva.business.mapper;

import br.net.silva.business.dto.ChangePasswordDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

@Mapper
public interface MapToChangePasswordMapper {

    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "agency", source = "agency")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "newPassword", source = "newPassword")
    ChangePasswordDTO mapToChangePasswordDto(Map<String, String> map);
}
