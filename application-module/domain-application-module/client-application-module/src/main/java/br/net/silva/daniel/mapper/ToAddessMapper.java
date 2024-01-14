package br.net.silva.daniel.mapper;

import br.net.silva.daniel.annotations.address.*;
import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.value_object.Source;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ToAddessMapper {

    ToAddessMapper INSTANCE = Mappers.getMapper(ToAddessMapper.class);

    @Mapping(target = "street", source = "map", qualifiedBy = AddressStreet.class)
    @Mapping(target = "city", source = "map", qualifiedBy = AddressCity.class)
    @Mapping(target = "complement", source = "map", qualifiedBy = AddressComplement.class)
    @Mapping(target = "neighborhood", source = "map", qualifiedBy = AddressNeighborhood.class)
    @Mapping(target = "number", source = "map", qualifiedBy = AddressNumber.class)
    @Mapping(target = "state", source = "map", qualifiedBy = AddressState.class)
    @Mapping(target = "zipCode", source = "map", qualifiedBy = AddressZipCode.class)
    AddressDTO toAddressDTO(Source source);
}
