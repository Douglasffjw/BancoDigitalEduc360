package br.com.cdb.bancodigitaljpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cdb.bancodigitaljpa.endity.Cliente;
import br.com.cdb.bancodigitaljpa.enuns.CategoriaCliente;

import java.math.BigDecimal;
import java.util.List;

@Repository // Indica que esta classe é um repositório Spring Data JPA
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
 // JpaRepository fornece métodos prontos para operações CRUD e consultas
 // O primeiro parâmetro é a entidade, o segundo é o tipo da chave primária
 // Não é necessário implementar nada aqui, o Spring Data JPA faz isso automaticamente
 // Você pode adicionar métodos personalizados se necessário, por exemplo:
   // Buscar clientes por categoria
    List<Cliente> findByCategoria(CategoriaCliente categoria);

    // Buscar clientes que possuem conta corrente
    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.contas ct WHERE TYPE(ct) = ContaCorrente")
    List<Cliente> findClientesComContaCorrente();

    // Buscar clientes que possuem conta poupança
    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.contas ct WHERE TYPE(ct) = ContaPoupanca")
    List<Cliente> findClientesComContaPoupanca();

    // Buscar clientes com saldo maior que um valor específico
    @Query("SELECT DISTINCT c FROM Cliente c JOIN c.contas ct WHERE ct.saldo > :valor")
    List<Cliente> findClientesComSaldoMaiorQue(@Param("valor") BigDecimal valor);
}
