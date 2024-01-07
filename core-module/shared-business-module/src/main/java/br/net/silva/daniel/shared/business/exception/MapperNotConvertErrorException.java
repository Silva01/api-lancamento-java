package br.net.silva.daniel.shared.business.exception;

public class MapperNotConvertErrorException extends RuntimeException {
    public MapperNotConvertErrorException(Throwable cause) {
        super(cause);
    }

    public MapperNotConvertErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
