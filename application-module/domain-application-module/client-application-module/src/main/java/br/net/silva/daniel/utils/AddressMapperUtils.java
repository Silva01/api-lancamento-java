package br.net.silva.daniel.utils;

import br.net.silva.daniel.annotations.address.AddressStreet;
import br.net.silva.daniel.dto.AddressRequestDTO;

import java.util.Map;

public class AddressMapperUtils {

    @AddressStreet
    public static AddressRequestDTO street(Map<String, String> map) {
        var street = ExtractMapUtils.extractMapValue(map, "street", String.class);
        var number = ExtractMapUtils.extractMapValue(map, "number", String.class);
        var complement = ExtractMapUtils.extractMapValue(map, "complement", String.class);
        var neighborhood = ExtractMapUtils.extractMapValue(map, "neighborhood", String.class);
        var state = ExtractMapUtils.extractMapValue(map, "state", String.class);
        var city = ExtractMapUtils.extractMapValue(map, "city", String.class);
        var zipCode = ExtractMapUtils.extractMapValue(map, "zipCode", String.class);
        return new AddressRequestDTO(street, number, complement, neighborhood, state, city, zipCode);
    }
}
