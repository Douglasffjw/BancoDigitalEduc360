package br.com.cdb.bancodigitaljpa.service;

import br.com.cdb.bancodigitaljpa.endity.Cartao;
import br.com.cdb.bancodigitaljpa.endity.CartaoCredito;
import br.com.cdb.bancodigitaljpa.endity.CartaoDebito;
import br.com.cdb.bancodigitaljpa.repository.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    // Criar um novo cartão
    public Cartao criarCartao(Cartao cartao) {
        return cartaoRepository.save(cartao);
    }

    // Obter detalhes de um cartão pelo ID
    public Optional<Cartao> obterCartaoPorId(Long id) {
        return cartaoRepository.findById(id);
    }

    // Alterar a senha de um cartão
    public void alterarSenha(Long id, String novaSenha) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));
        cartao.setSenha(novaSenha);
        cartaoRepository.save(cartao);
    }

    // Ativar ou desativar um cartão
    public void ativarOuDesativarCartao(Long id, boolean ativo) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));
        cartao.setAtivo(ativo);
        cartaoRepository.save(cartao);
    }

    // Ajustar limite diário do cartão de débito
    public void ajustarLimiteCartaoDebito(Long id, BigDecimal novoLimite) {
        if (novoLimite.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O limite diário deve ser maior que zero.");
        }
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));
        if (cartao instanceof CartaoDebito) {
            ((CartaoDebito) cartao).setLimiteDiario(novoLimite);
            cartaoRepository.save(cartao);
        } else {
            throw new IllegalArgumentException("O cartão informado não é um cartão de débito.");
        }
    }

    // Alterar limite do cartão de crédito
    public void alterarLimiteCartaoCredito(Long id, BigDecimal novoLimite) {
        if (novoLimite.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O limite do cartão deve ser maior que zero.");
        }
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));
        if (cartao instanceof CartaoCredito) {
            ((CartaoCredito) cartao).setLimite(novoLimite);
            cartaoRepository.save(cartao);
        } else {
            throw new IllegalArgumentException("O cartão informado não é um cartão de crédito.");
        }
    }

    // Realizar pagamento com o cartão
    public void realizarPagamento(Long id, BigDecimal valor) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));
        if (cartao instanceof CartaoCredito) {
            CartaoCredito cartaoCredito = (CartaoCredito) cartao;
            if (!cartaoCredito.podeRealizarPagamento(valor)) {
                throw new IllegalArgumentException("Saldo insuficiente ou limite excedido.");
            }
            cartaoCredito.realizarPagamento(valor);
        } else if (cartao instanceof CartaoDebito) {
            CartaoDebito cartaoDebito = (CartaoDebito) cartao;
            if (!cartaoDebito.podeRealizarPagamento(valor)) {
                throw new IllegalArgumentException("Saldo insuficiente ou limite diário excedido.");
            }
            cartaoDebito.realizarPagamento(valor);
        } else {
            throw new IllegalArgumentException("Tipo de cartão inválido.");
        }
        cartaoRepository.save(cartao);
    }

    // Consultar fatura do cartão de crédito
    public BigDecimal consultarFaturaCartaoCredito(Long id) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));
        if (cartao instanceof CartaoCredito) {
            return ((CartaoCredito) cartao).getSaldoUtilizado();
        } else {
            throw new IllegalArgumentException("O cartão informado não é um cartão de crédito.");
        }
    }

    // Realizar pagamento da fatura do cartão de crédito
    public void pagarFaturaCartaoCredito(Long id, BigDecimal valor) {
        Cartao cartao = cartaoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));
        if (cartao instanceof CartaoCredito) {
            CartaoCredito cartaoCredito = (CartaoCredito) cartao;
            BigDecimal saldoUtilizado = cartaoCredito.getSaldoUtilizado();
            if (valor.compareTo(saldoUtilizado) > 0) {
                throw new IllegalArgumentException("O valor do pagamento excede o saldo da fatura.");
            }
            cartaoCredito.setSaldoUtilizado(saldoUtilizado.subtract(valor));
            cartaoRepository.save(cartaoCredito);
        } else {
            throw new IllegalArgumentException("O cartão informado não é um cartão de crédito.");
        }
    }
}