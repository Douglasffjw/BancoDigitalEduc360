package br.com.cdb.bancodigitaljpa.service;

import br.com.cdb.bancodigitaljpa.endity.Conta;
import br.com.cdb.bancodigitaljpa.endity.ContaCorrente;
import br.com.cdb.bancodigitaljpa.endity.ContaPoupanca;
import br.com.cdb.bancodigitaljpa.repository.ContaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ContaService {

    private static final Logger logger = LoggerFactory.getLogger(ContaService.class);

    @Autowired
    private ContaRepository contaRepository;

    // Criar uma nova conta
    public Conta criarConta(Conta conta) {
        if (conta.getCliente() == null || conta.getCliente().getId() == null) {
            throw new IllegalArgumentException("Cliente inválido.");
        }
        logger.info("Criando uma nova conta para o cliente com ID: {}", conta.getCliente().getId());
        return contaRepository.save(conta);
    }

    // Obter detalhes de uma conta pelo ID
    public Optional<Conta> obterContaPorId(Long id) {
        logger.info("Buscando conta com ID: {}", id);
        return contaRepository.findById(id);
    }

    // Consultar saldo de uma conta
    public Optional<BigDecimal> consultarSaldo(Long id) {
        logger.info("Consultando saldo da conta com ID: {}", id);
        return contaRepository.findById(id).map(Conta::getSaldo);
    }

    // Realizar um depósito
    public boolean realizarDeposito(Long id, BigDecimal valor) {
        validarValorPositivo(valor);
        logger.info("Realizando depósito de {} na conta com ID: {}", valor, id);
        return contaRepository.findById(id).map(conta -> {
            conta.depositar(valor);
            contaRepository.save(conta);
            return true;
        }).orElse(false);
    }

    // Realizar um saque
    public boolean realizarSaque(Long id, BigDecimal valor) {
        validarValorPositivo(valor);
        logger.info("Realizando saque de {} na conta com ID: {}", valor, id);
        return contaRepository.findById(id).map(conta -> {
            try {
                conta.sacar(valor);
                contaRepository.save(conta);
                return true;
            } catch (IllegalArgumentException e) {
                logger.error("Erro ao realizar saque: {}", e.getMessage());
                return false; // Saldo insuficiente
            }
        }).orElse(false);
    }

    // Realizar uma transferência entre contas
    public boolean realizarTransferencia(Long contaOrigemId, Long contaDestinoId, BigDecimal valor) {
        validarValorPositivo(valor);
        logger.info("Iniciando transferência de {} da conta {} para a conta {}", valor, contaOrigemId, contaDestinoId);
        Optional<Conta> contaOrigemOpt = contaRepository.findById(contaOrigemId);
        Optional<Conta> contaDestinoOpt = contaRepository.findById(contaDestinoId);

        if (contaOrigemOpt.isPresent() && contaDestinoOpt.isPresent()) {
            Conta contaOrigem = contaOrigemOpt.get();
            Conta contaDestino = contaDestinoOpt.get();

            try {
                contaOrigem.sacar(valor);
                contaDestino.depositar(valor);
                contaRepository.save(contaOrigem);
                contaRepository.save(contaDestino);
                return true;
            } catch (IllegalArgumentException e) {
                logger.error("Erro ao realizar transferência: {}", e.getMessage());
                return false; // Saldo insuficiente
            }
        }
        logger.warn("Transferência não realizada. Uma das contas não existe.");
        return false;
    }

    // Realizar um pagamento via Pix
    public boolean realizarPix(Long id, BigDecimal valor) {
        logger.info("Realizando pagamento via Pix de {} na conta com ID: {}", valor, id);
        return realizarSaque(id, valor); // Pix é tratado como um saque
    }

    // Aplicar taxa de manutenção mensal (para conta corrente)
    public boolean aplicarTaxaManutencao(Long id, String tipoCliente) {
        logger.info("Aplicando taxa de manutenção na conta com ID: {}", id);
        return contaRepository.findById(id).map(conta -> {
            if (conta instanceof ContaCorrente) {
                try {
                    ((ContaCorrente) conta).descontarTaxaManutencao(tipoCliente);
                    contaRepository.save(conta);
                    return true;
                } catch (IllegalArgumentException e) {
                    logger.error("Erro ao aplicar taxa de manutenção: {}", e.getMessage());
                    return false; // Saldo insuficiente ou tipo de cliente inválido
                }
            }
            logger.warn("Conta com ID {} não é do tipo ContaCorrente.", id);
            return false; // Não é uma conta corrente
        }).orElse(false);
    }

    // Aplicar rendimentos (para conta poupança)
    public boolean aplicarRendimentos(Long id, String tipoCliente) {
        logger.info("Aplicando rendimentos na conta com ID: {}", id);
        return contaRepository.findById(id).map(conta -> {
            if (conta instanceof ContaPoupanca) {
                try {
                    ContaPoupanca contaPoupanca = (ContaPoupanca) conta;
                    BigDecimal rendimento = contaPoupanca.calcularRendimentoMensal(tipoCliente, conta.getSaldo());
                    conta.depositar(rendimento);
                    contaRepository.save(conta);
                    return true;
                } catch (IllegalArgumentException e) {
                    logger.error("Erro ao aplicar rendimentos: {}", e.getMessage());
                    return false; // Tipo de cliente inválido
                }
            }
            logger.warn("Conta com ID {} não é do tipo ContaPoupanca.", id);
            return false; // Não é uma conta poupança
        }).orElse(false);
    }

    // Validação de valores positivos
    private void validarValorPositivo(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }
    }
}