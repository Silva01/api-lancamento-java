package com.example.api_de_lancamentos.domain.account.dto;

public final class AccountErrorDTO {
    private final String message;

    public AccountErrorDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
