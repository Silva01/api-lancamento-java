package br.net.silva.daniel.shared.application.gateway;

public interface DeleteApplicationBaseGateway {
    boolean deleteById(ParamGateway param);
    boolean deleteAll();
}
