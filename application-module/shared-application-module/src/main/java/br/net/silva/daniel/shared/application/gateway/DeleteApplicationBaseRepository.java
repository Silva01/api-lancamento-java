package br.net.silva.daniel.shared.application.gateway;

public interface DeleteApplicationBaseRepository {
    boolean deleteById(ParamRepository param);
    boolean deleteAll();
}
