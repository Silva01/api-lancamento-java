package com.example.api_de_lancamentos.domain.shared.interfaces;


public interface UseCase<R,E> {
    R execute(E entity);
}
