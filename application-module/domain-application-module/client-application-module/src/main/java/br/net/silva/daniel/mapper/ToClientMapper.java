package br.net.silva.daniel.mapper;

import br.net.silva.daniel.annotations.client.*;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.dto.ClientRequestDTO;
import br.net.silva.daniel.utils.ClientRequestMapperUtils;
import br.net.silva.daniel.shared.business.value_object.Source;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper(uses = {ClientRequestMapperUtils.class})
public interface ToClientMapper {

    ToClientMapper INSTANCE = Mappers.getMapper(ToClientMapper.class);

    @Mapping(target = "id", source = "map", qualifiedBy = ClientId.class)
    @Mapping(target = "cpf", source = "map", qualifiedBy = ClientCpf.class)
    @Mapping(target = "name", source = "map", qualifiedBy = ClientName.class)
    @Mapping(target = "active", source = "map", qualifiedBy = ClientActive.class)
    @Mapping(target = "telephone", source = "map", qualifiedBy = ClientTelephone.class)
    @Mapping(target = "addressRequestDTO", source = "map", qualifiedBy = ClientAddressRequest.class)
    ClientRequestDTO toClientRequestDTO(Source source);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "telephone", source = "telephone")
    @Mapping(target = "addressRequestDTO", ignore = true)
    ClientRequestDTO toClientRequestDTO(Map<String, String> map);

    @Mapping(target = "id", source = "map", qualifiedBy = ClientId.class)
    @Mapping(target = "cpf", source = "map", qualifiedBy = ClientCpf.class)
    @Mapping(target = "name", source = "map", qualifiedBy = ClientName.class)
    @Mapping(target = "active", source = "map", qualifiedBy = ClientActive.class)
    @Mapping(target = "telephone", source = "map", qualifiedBy = ClientTelephone.class)
    @Mapping(target = "address", source = "map", qualifiedBy = ClientAddress.class)
    ClientDTO toClientDTO(Source source);
}
