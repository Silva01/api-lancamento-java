package com.example.api_de_lancamentos.infrastructure.repository;

import com.example.api_de_lancamentos.infrastructure.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountModelRepository extends JpaRepository<Account, Long> {
}
