package br.com.cdb.bancodigitaljpa.endity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Seguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private BigDecimal valorMensal;

    @Column(nullable = false)
    private BigDecimal cobertura;

    @Column(nullable = false)
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "cartao_id", nullable = false)
    private Cartao cartao;

    public Seguro() {
    }

    public Seguro(String tipo, BigDecimal valorMensal, BigDecimal cobertura, Cartao cartao) {
        this.tipo = tipo;
        this.valorMensal = valorMensal;
        this.cobertura = cobertura;
        this.cartao = cartao;
        this.ativo = true;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValorMensal() {
        return valorMensal;
    }

    public void setValorMensal(BigDecimal valorMensal) {
        this.valorMensal = valorMensal;
    }

    public BigDecimal getCobertura() {
        return cobertura;
    }

    public void setCobertura(BigDecimal cobertura) {
        this.cobertura = cobertura;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}