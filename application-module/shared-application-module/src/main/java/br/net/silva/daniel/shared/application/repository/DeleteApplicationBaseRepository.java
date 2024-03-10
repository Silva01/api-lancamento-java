package br.net.silva.daniel.shared.application.repository;

public interface DeleteApplicationBaseRepository {
    boolean deleteById(ParamRepository param);
    boolean deleteAll();
}
