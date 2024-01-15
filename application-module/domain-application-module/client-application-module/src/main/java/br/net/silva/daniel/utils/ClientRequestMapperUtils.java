package br.net.silva.daniel.utils;

import br.net.silva.daniel.annotations.client.*;
import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.dto.AddressRequestDTO;
import br.net.silva.daniel.enums.TypeClientMapperEnum;
import br.net.silva.daniel.mapper.ToAddessMapper;
import br.net.silva.daniel.shared.business.value_object.Source;

import java.util.Map;

public class ClientRequestMapperUtils {

    @ClientId
    public static String id(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, TypeClientMapperEnum.CLIENT.name(), "id", String.class);
    }

    @ClientCpf
    public static String cpf(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, TypeClientMapperEnum.CLIENT.name(), "cpf", String.class);
    }

    @ClientName
    public static String name(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, TypeClientMapperEnum.CLIENT.name(), "name", String.class);
    }

    @ClientTelephone
    public static String telephone(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, TypeClientMapperEnum.CLIENT.name(), "telephone", String.class);
    }

    @ClientActive
    public static Boolean active(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, TypeClientMapperEnum.CLIENT.name(), "active", Boolean.class);
    }

    @ClientAgency
    public static Integer agency(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(map, TypeClientMapperEnum.CLIENT.name(), "agency", Integer.class);
    }

    @ClientAddressRequest
    public static AddressRequestDTO addressRequest(Map<String, Object> map) {
        return convertAddress(map, AddressRequestDTO.class);
    }

    @ClientAddress
    public static AddressDTO address(Map<String, Object> map) {
        return convertAddress(map, AddressDTO.class);
    }

    @SuppressWarnings("unchecked")
    private static <T> T convertAddress(Map<String, Object> map, Class<T> clazz) {
        if (map.containsKey(TypeClientMapperEnum.CLIENT.name())) {
            var mapClient = (Map<String, Object>) map.get(TypeClientMapperEnum.CLIENT.name());
            if (mapClient.containsKey(TypeClientMapperEnum.ADDRESS.name().toLowerCase())) {
                if (clazz.equals(AddressRequestDTO.class)) {
                    return clazz.cast(ToAddessMapper.INSTANCE.toAddressRequestDTO(new Source(map, null))) ;
                } else if (clazz.equals(AddressDTO.class)) {
                    return clazz.cast(ToAddessMapper.INSTANCE.toAddressDTO(new Source(map, null)));
                }
            }
        }
        return null;
    }
}
