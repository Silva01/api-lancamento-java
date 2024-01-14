package br.net.silva.daniel.utils;

import br.net.silva.daniel.annotations.client.*;
import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.enums.TypeClientMapperEnum;
import br.net.silva.daniel.mapper.ToAddessMapper;
import br.net.silva.daniel.value_object.Source;

import java.util.Map;

public class ClientMapperUtils {

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

    @ClientAddress
    @SuppressWarnings("unchecked")
    public static AddressDTO address(Map<String, Object> map) {
        if (map.containsKey(TypeClientMapperEnum.CLIENT.name())) {
            var mapClient = (Map<String, Object>) map.get(TypeClientMapperEnum.CLIENT.name());
            if (mapClient.containsKey(TypeClientMapperEnum.ADDRESS.name())) {
                return ToAddessMapper.INSTANCE.toAddressDTO(new Source(map, null));
            }
        }
        return null;
    }
}
