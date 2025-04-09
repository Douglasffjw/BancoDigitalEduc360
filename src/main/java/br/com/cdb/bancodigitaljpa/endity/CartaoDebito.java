package br.com.cdb.bancodigitaljpa.endity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class CartaoDebito extends Cartao {

    @Column(nullable = false)
    private BigDecimal limiteDiario;

    @Column(nullable = false)
    private BigDecimal totalUtilizadoHoje;

    public CartaoDebito() {
        this.totalUtilizadoHoje = BigDecimal.ZERO;
    }

    /**
     * Obtém o limite diário do cartão de débito.
     * @return Limite diário.
     */
    public BigDecimal getLimiteDiario() {
        return limiteDiario;
    }

    /**
     * Define o limite diário do cartão de débito.
     * @param limiteDiario Novo limite diário.
     */
    public void setLimiteDiario(BigDecimal limiteDiario) {
        if (limiteDiario.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O limite diário não pode ser negativo.");
        }
        this.limiteDiario = limiteDiario;
    }

    /**
     * Obtém o total utilizado hoje.
     * @return Total utilizado hoje.
     */
    public BigDecimal getTotalUtilizadoHoje() {
        return totalUtilizadoHoje;
    }

    /**
     * Define o total utilizado hoje.
     * @param totalUtilizadoHoje Novo total utilizado hoje.
     */
    public void setTotalUtilizadoHoje(BigDecimal totalUtilizadoHoje) {
        this.totalUtilizadoHoje = totalUtilizadoHoje;
    }

    /**
     * Verifica se o pagamento pode ser realizado com base no limite diário.
     * @param valor Valor do pagamento.
     * @return True se o pagamento puder ser realizado, false caso contrário.
     */
    public boolean podeRealizarPagamento(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor do pagamento deve ser positivo.");
        }
        return totalUtilizadoHoje.add(valor).compareTo(limiteDiario) <= 0;
    }

    /**
     * Realiza um pagamento, adicionando o valor ao total utilizado hoje.
     * @param valor Valor do pagamento.
     */
    public void realizarPagamento(BigDecimal valor) {
        if (!podeRealizarPagamento(valor)) {
            throw new IllegalArgumentException("Limite diário do cartão de débito excedido.");
        }
        totalUtilizadoHoje = totalUtilizadoHoje.add(valor);
    }

    /**
     * Redefine o total utilizado hoje para zero.
     * Deve ser chamado ao final do dia.
     */
    public void resetarTotalUtilizadoHoje() {
        this.totalUtilizadoHoje = BigDecimal.ZERO;
    }
}