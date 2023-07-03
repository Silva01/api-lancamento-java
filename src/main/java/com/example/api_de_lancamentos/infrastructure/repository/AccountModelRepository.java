package com.example.api_de_lancamentos.infrastructure.repository;

import com.example.api_de_lancamentos.infrastructure.model.AccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountModelRepository extends JpaRepository<AccountModel, Long> {
}
