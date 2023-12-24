package br.net.silva.daniel.exception;

public class ProcessExecErrorException extends RuntimeException {
    public ProcessExecErrorException(String message) {
        super(message);
    }

    public ProcessExecErrorException(Throwable cause) {
        super(cause);
    }
}
