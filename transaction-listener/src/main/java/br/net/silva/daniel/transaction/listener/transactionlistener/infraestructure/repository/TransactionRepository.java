package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.repository;

import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
