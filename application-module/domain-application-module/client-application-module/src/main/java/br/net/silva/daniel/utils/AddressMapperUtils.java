package br.net.silva.daniel.utils;

import br.net.silva.daniel.annotations.address.*;
import br.net.silva.daniel.enums.TypeClientMapperEnum;

import java.util.Map;

public class AddressMapperUtils {

    @AddressStreet
    public static String street(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(extractAddressMap(map), "street", String.class);
    }

    @AddressNumber
    public static String number(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(extractAddressMap(map), "number", String.class);
    }

    @AddressComplement
    public static String complement(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(extractAddressMap(map), "complement", String.class);
    }

    @AddressNeighborhood
    public static String neighborhood(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(extractAddressMap(map), "neighborhood", String.class);
    }

    @AddressState
    public static String state(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(extractAddressMap(map), "state", String.class);
    }

    @AddressCity
    public static String city(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(extractAddressMap(map), "city", String.class);
    }

    @AddressZipCode
    public static String zipCode(Map<String, Object> map) {
        return ExtractMapUtils.extractMapValue(extractAddressMap(map), "zipCode", String.class);
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> extractAddressMap(Map<String, Object> map) {
        if (map.containsKey(TypeClientMapperEnum.CLIENT.name())) {
            var mapClient = (Map<String, Object>) map.get(TypeClientMapperEnum.CLIENT.name());
            if (mapClient.containsKey(TypeClientMapperEnum.ADDRESS.name())) {
                return (Map<String, Object>) mapClient.get(TypeClientMapperEnum.ADDRESS.name());
            }
        }

        if (map.containsKey(TypeClientMapperEnum.ADDRESS.name())) {
            return (Map<String, Object>) map.get(TypeClientMapperEnum.ADDRESS.name());
        }

        throw new IllegalArgumentException("Address not found");
    }
}
