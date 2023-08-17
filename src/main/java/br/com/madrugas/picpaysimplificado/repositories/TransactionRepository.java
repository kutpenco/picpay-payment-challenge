package br.com.madrugas.picpaysimplificado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.madrugas.picpaysimplificado.domain.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
