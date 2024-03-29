package br.net.silva.daniel.shared.application.interfaces;

public interface ICreditCardParam extends Input {
    String creditCardNumber();
    Integer creditCardCvv();
    String cpf();
}
