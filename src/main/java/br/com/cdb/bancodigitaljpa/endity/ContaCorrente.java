package br.com.cdb.bancodigitaljpa.endity;

import jakarta.persistence.Entity;
import java.math.BigDecimal;

/**
 * Classe que representa uma conta corrente.
 * Possui uma taxa de manutenção mensal que pode ser descontada do saldo.
 */
@Entity
public class ContaCorrente extends Conta {

    public ContaCorrente() {
        super();
    }

    public ContaCorrente(Cliente cliente) {
        super(cliente);
    }

    /**
     * Calcula a taxa de manutenção mensal com base no tipo de cliente.
     * @param tipoCliente Tipo do cliente (Comum, Super, Premium).
     * @return Taxa de manutenção mensal.
     */
    public BigDecimal calcularTaxaManutencao(String tipoCliente) {
        switch (tipoCliente.toLowerCase()) {
            case "comum":
                return BigDecimal.valueOf(12.00);
            case "super":
                return BigDecimal.valueOf(8.00);
            case "premium":
                return BigDecimal.ZERO;
            default:
                throw new IllegalArgumentException("Tipo de cliente inválido.");
        }
    }

    /**
     * Aplica a taxa de manutenção mensal à conta corrente.
     * Lança uma exceção se o saldo for insuficiente.
     * @param tipoCliente Tipo do cliente (Comum, Super, Premium).
     */
    public void descontarTaxaManutencao(String tipoCliente) {
        BigDecimal taxa = calcularTaxaManutencao(tipoCliente);
        sacar(taxa);
    }
}