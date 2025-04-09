package br.com.cdb.bancodigitaljpa.endity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
public class CartaoCredito extends Cartao {

    @Column(nullable = false)
    private BigDecimal limite;

    @Column(nullable = false)
    private BigDecimal saldoUtilizado;

    public CartaoCredito() {
        this.saldoUtilizado = BigDecimal.ZERO;
    }

    /**
     * Define o limite de crédito com base no tipo de cliente.
     * @param tipoCliente Tipo do cliente (Comum, Super, Premium).
     */
    public void definirLimite(String tipoCliente) {
        switch (tipoCliente.toLowerCase()) {
            case "comum":
                this.limite = BigDecimal.valueOf(1000.00);
                break;
            case "super":
                this.limite = BigDecimal.valueOf(5000.00);
                break;
            case "premium":
                this.limite = BigDecimal.valueOf(10000.00);
                break;
            default:
                throw new IllegalArgumentException("Tipo de cliente inválido.");
        }
    }

    /**
     * Aplica a taxa de utilização se o total gasto exceder 80% do limite de crédito.
     * @return O valor da taxa de utilização aplicada.
     */
    public BigDecimal aplicarTaxaDeUtilizacao() {
        BigDecimal limite80 = limite.multiply(BigDecimal.valueOf(0.8));
        if (saldoUtilizado.compareTo(limite80) > 0) {
            BigDecimal taxa = saldoUtilizado.multiply(BigDecimal.valueOf(0.05));
            saldoUtilizado = saldoUtilizado.add(taxa);
            return taxa.setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Verifica se o pagamento pode ser realizado com base no limite disponível.
     * @param valor Valor do pagamento.
     * @return True se o pagamento puder ser realizado, false caso contrário.
     */
    public boolean podeRealizarPagamento(BigDecimal valor) {
        return saldoUtilizado.add(valor).compareTo(limite) <= 0;
    }

    /**
     * Realiza um pagamento, adicionando o valor ao saldo utilizado.
     * @param valor Valor do pagamento.
     */
    public void realizarPagamento(BigDecimal valor) {
        if (!podeRealizarPagamento(valor)) {
            throw new IllegalArgumentException("Limite do cartão de crédito excedido.");
        }
        saldoUtilizado = saldoUtilizado.add(valor);
    }

    // Getters e Setters
    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }

    public BigDecimal getSaldoUtilizado() {
        return saldoUtilizado;
    }

    public void setSaldoUtilizado(BigDecimal saldoUtilizado) {
        this.saldoUtilizado = saldoUtilizado;
    }
}