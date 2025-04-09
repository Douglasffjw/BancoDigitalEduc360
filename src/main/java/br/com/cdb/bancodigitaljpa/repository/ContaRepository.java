package br.com.cdb.bancodigitaljpa.repository;

import br.com.cdb.bancodigitaljpa.endity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    // JpaRepository já fornece métodos básicos como save, findById, findAll, deleteById, etc.
    // Métodos personalizados podem ser adicionados aqui, se necessário
}