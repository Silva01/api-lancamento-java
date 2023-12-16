package br.net.silva.daniel.exception;

public class AccountExistsForCPFInformatedException extends GenericException {
    public AccountExistsForCPFInformatedException(String message) {
        super(message);
    }
}
