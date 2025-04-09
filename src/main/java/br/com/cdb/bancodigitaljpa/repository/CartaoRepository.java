package br.com.cdb.bancodigitaljpa.repository;

import br.com.cdb.bancodigitaljpa.endity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
}