package com.example.api_de_lancamentos.domain.shared.interfaces;

public interface UpdateRepository <E> {
    E update(E entity);
}
