package br.net.silva.daniel.mapper;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.dto.ClientRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface ToClientMapper {

    ToClientMapper INSTANCE = Mappers.getMapper(ToClientMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "telephone", source = "telephone")
    @Mapping(target = "addressRequestDTO.zipCode", source = "zipCode")
    @Mapping(target = "addressRequestDTO.number", source = "number")
    @Mapping(target = "addressRequestDTO.state", source = "state")
    @Mapping(target = "addressRequestDTO.street", source = "street")
    @Mapping(target = "addressRequestDTO.city", source = "city")
    @Mapping(target = "addressRequestDTO.neighborhood", source = "neighborhood")
    @Mapping(target = "addressRequestDTO.complement", source = "complement")
    ClientRequestDTO toClientRequestDTO(Map<String, String> map);

    @Mapping(target = "address", source = "addressRequestDTO")
    ClientDTO toClientDTO(ClientRequestDTO clientRequestDTO);
}
