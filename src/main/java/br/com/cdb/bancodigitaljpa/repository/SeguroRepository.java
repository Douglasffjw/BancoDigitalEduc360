package br.com.cdb.bancodigitaljpa.repository;

import br.com.cdb.bancodigitaljpa.endity.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguroRepository extends JpaRepository<Seguro, Long> {
}