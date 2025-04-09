package br.com.cdb.bancodigitaljpa.endity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Estratégia de herança no JPA
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipo"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ContaCorrente.class, name = "corrente"),
    @JsonSubTypes.Type(value = ContaPoupanca.class, name = "poupanca")
})
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal saldo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false) // Define explicitamente a coluna de chave estrangeira
    @JsonBackReference // Resolve a referência circular
    private Cliente cliente;

    public Conta() {
        this.saldo = BigDecimal.ZERO; // Saldo inicial padrão
    }

    public Conta(Cliente cliente) {
        this.cliente = cliente;
        this.saldo = BigDecimal.ZERO;
    }

    // Métodos para manipular o saldo
    public void depositar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
    }

    public void sacar(BigDecimal valor) {
        if (this.saldo.compareTo(valor) >= 0) {
            this.saldo = this.saldo.subtract(valor);
        } else {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}