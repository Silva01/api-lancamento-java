package br.net.silva.daniel.mapper;

import br.net.silva.daniel.value_object.input.AddressRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface ToAddessMapper {

    ToAddessMapper INSTANCE = Mappers.getMapper(ToAddessMapper.class);

    @Mapping(target = "street", source = "street")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "complement", source = "complement")
    @Mapping(target = "neighborhood", source = "neighborhood")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "zipCode", source = "zipCode")
    AddressRequestDTO toAddressRequestDTO(Map<String, String> map);
}
