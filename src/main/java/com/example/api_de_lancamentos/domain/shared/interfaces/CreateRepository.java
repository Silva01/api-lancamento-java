package com.example.api_de_lancamentos.domain.shared.interfaces;

public interface CreateRepository <E> {
    E create(E entity);
}
