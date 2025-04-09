package br.com.cdb.bancodigitaljpa.endity;

import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Classe que representa uma conta poupança.
 * Possui uma funcionalidade de aplicar rendimentos mensais ao saldo.
 */
@Entity
public class ContaPoupanca extends Conta {

    public ContaPoupanca() {
        super();
    }

    public ContaPoupanca(Cliente cliente) {
        super(cliente);
    }

    /**
     * Calcula o rendimento mensal com base no tipo de cliente e no saldo atual.
     * @param tipoCliente Tipo do cliente (Comum, Super, Premium).
     * @param saldoAtual Saldo atual da conta.
     * @return O valor do rendimento mensal.
     */
    public BigDecimal calcularRendimentoMensal(String tipoCliente, BigDecimal saldoAtual) {
        double taxaAnual;
        switch (tipoCliente.toLowerCase()) {
            case "comum":
                taxaAnual = 0.005; // 0,5% ao ano
                break;
            case "super":
                taxaAnual = 0.007; // 0,7% ao ano
                break;
            case "premium":
                taxaAnual = 0.009; // 0,9% ao ano
                break;
            default:
                throw new IllegalArgumentException("Tipo de cliente inválido.");
        }

        // Fórmula do juro composto: M = P * (1 + i)^n
        double taxaMensal = Math.pow(1 + taxaAnual, 1.0 / 12) - 1;
        BigDecimal rendimento = saldoAtual.multiply(BigDecimal.valueOf(taxaMensal));
        return rendimento.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Aplica o rendimento mensal à conta poupança com base no tipo de cliente.
     * @param tipoCliente Tipo do cliente (Comum, Super, Premium).
     */
    public void aplicarRendimento(String tipoCliente) {
        if (getSaldo().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal rendimento = calcularRendimentoMensal(tipoCliente, getSaldo());
            depositar(rendimento);
        }
    }
}