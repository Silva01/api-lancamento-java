package br.net.silva.daniel.mapper;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.shared.application.interfaces.IMapperResponse;
import br.net.silva.daniel.shared.application.interfaces.Output;
import br.net.silva.daniel.value_object.output.GetInformationClientResponse;

public class CreateResponseToGetInformationClientFactory implements IMapperResponse<ClientDTO, Output> {
    @Override
    public boolean accept(Object input, Output output) {
        return input instanceof ClientDTO && output instanceof GetInformationClientResponse;
    }

    @Override
    public void toFillIn(ClientDTO input, Output output) {
        ((GetInformationClientResponse) output).setCpf(input.cpf());
        ((GetInformationClientResponse) output).setName(input.name());
        ((GetInformationClientResponse) output).setTelephone(input.telephone());
        ((GetInformationClientResponse) output).setStreet(input.address().street());
        ((GetInformationClientResponse) output).setNeighborhood(input.address().neighborhood());
        ((GetInformationClientResponse) output).setNumber(input.address().number());
        ((GetInformationClientResponse) output).setComplement(input.address().complement());
        ((GetInformationClientResponse) output).setCity(input.address().city());
        ((GetInformationClientResponse) output).setState(input.address().state());
        ((GetInformationClientResponse) output).setZipCod(input.address().zipCode());
    }
}
