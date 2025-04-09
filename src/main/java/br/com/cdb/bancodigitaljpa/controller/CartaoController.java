package br.com.cdb.bancodigitaljpa.controller;

import br.com.cdb.bancodigitaljpa.endity.Cartao;
import br.com.cdb.bancodigitaljpa.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    @Autowired
    private CartaoService cartaoService;

    // POST /cartoes - Emitir um novo cartão
    @PostMapping
    public ResponseEntity<Cartao> emitirCartao(@RequestBody Cartao cartao) {
        return ResponseEntity.ok(cartaoService.criarCartao(cartao));
    }

    // GET /cartoes/{id} - Obter detalhes de um cartão
    @GetMapping("/{id}")
    public ResponseEntity<Cartao> obterCartaoPorId(@PathVariable Long id) {
        return cartaoService.obterCartaoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /cartoes/{id}/pagamento - Realizar um pagamento com o cartão
    @PostMapping("/{id}/pagamento")
    public ResponseEntity<String> realizarPagamento(@PathVariable Long id, @RequestParam BigDecimal valor) {
        try {
            cartaoService.realizarPagamento(id, valor);
            return ResponseEntity.ok("Pagamento realizado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /cartoes/{id}/limite - Alterar limite do cartão
    @PutMapping("/{id}/limite")
    public ResponseEntity<String> alterarLimite(@PathVariable Long id, @RequestParam BigDecimal novoLimite) {
        cartaoService.alterarLimiteCartaoCredito(id, novoLimite);
        return ResponseEntity.ok("Limite do cartão alterado com sucesso.");
    }

    // PUT /cartoes/{id}/status - Ativar ou desativar um cartão
    @PutMapping("/{id}/status")
    public ResponseEntity<String> alterarStatus(@PathVariable Long id, @RequestParam boolean ativo) {
        cartaoService.ativarOuDesativarCartao(id, ativo);
        return ResponseEntity.ok(ativo ? "Cartão ativado com sucesso." : "Cartão desativado com sucesso.");
    }

    // PUT /cartoes/{id}/senha - Alterar senha do cartão
    @PutMapping("/{id}/senha")
    public ResponseEntity<String> alterarSenha(@PathVariable Long id, @RequestParam String novaSenha) {
        cartaoService.alterarSenha(id, novaSenha);
        return ResponseEntity.ok("Senha do cartão alterada com sucesso.");
    }

    // GET /cartoes/{id}/fatura - Consultar fatura do cartão de crédito
    @GetMapping("/{id}/fatura")
    public ResponseEntity<BigDecimal> consultarFatura(@PathVariable Long id) {
        BigDecimal fatura = cartaoService.consultarFaturaCartaoCredito(id);
        return ResponseEntity.ok(fatura);
    }

    // POST /cartoes/{id}/fatura/pagamento - Realizar pagamento da fatura do cartão de crédito
    @PostMapping("/{id}/fatura/pagamento")
    public ResponseEntity<String> pagarFatura(@PathVariable Long id, @RequestParam BigDecimal valor) {
        try {
            cartaoService.pagarFaturaCartaoCredito(id, valor);
            return ResponseEntity.ok("Pagamento da fatura realizado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /cartoes/{id}/limite-diario - Alterar limite diário do cartão de débito
    @PutMapping("/{id}/limite-diario")
    public ResponseEntity<String> alterarLimiteDiario(@PathVariable Long id, @RequestParam BigDecimal novoLimite) {
        cartaoService.ajustarLimiteCartaoDebito(id, novoLimite);
        return ResponseEntity.ok("Limite diário do cartão alterado com sucesso.");
    }

    // Tratamento global de exceções
    @ControllerAdvice
    public static class GlobalExceptionHandler {
        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}