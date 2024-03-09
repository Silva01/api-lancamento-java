package br.net.silva.daniel.repository;

public interface DeleteApplicationBaseRepository {
    boolean deleteById(ParamRepository param);
    boolean deleteAll();
}
