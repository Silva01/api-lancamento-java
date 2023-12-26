package br.net.silva.daniel.dto;

import br.net.silva.daniel.interfaces.IAccountPort;

public record ChangePasswordDTO(
        String cpf,
        Integer agency,
        Integer account,
        String password,
        String oldPassword
) implements IAccountPort {
    @Override
    public Integer accountNumber() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String newPassword() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isWithCreditCard() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
