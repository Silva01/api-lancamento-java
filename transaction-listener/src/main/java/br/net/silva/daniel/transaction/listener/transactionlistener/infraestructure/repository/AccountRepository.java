package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.repository;

import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>{

    @Query("SELECT a FROM Account a WHERE a.keys.number = ?1 AND a.keys.bankAgencyNumber = ?2 AND a.cpf = ?3")
    Optional<Account> findByAccountNumberAndAgencyAndCpf(Integer accountNumber, Integer agency, String cpf);
}
