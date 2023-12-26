package br.net.silva.daniel.interfaces;

public interface IAccountPort extends IGenericPort {
    Integer accountNumber();
    Integer agency();
    String cpf();
    String password();
    String newPassword();
    boolean isWithCreditCard();
}
