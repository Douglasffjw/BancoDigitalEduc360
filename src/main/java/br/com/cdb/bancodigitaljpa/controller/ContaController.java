package br.com.cdb.bancodigitaljpa.controller;

import br.com.cdb.bancodigitaljpa.endity.Conta;
import br.com.cdb.bancodigitaljpa.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/contas") // Define o caminho base para os endpoints
public class ContaController {

    @Autowired
    private ContaService contaService;

    // POST /contas - Criar uma nova conta
    @PostMapping
    public ResponseEntity<Conta> criarConta(@RequestBody Conta conta) {
        Conta novaConta = contaService.criarConta(conta);
        return ResponseEntity.ok(novaConta);
    }

    // GET /contas/{id} - Obter detalhes de uma conta
    @GetMapping("/{id}")
    public ResponseEntity<Conta> obterContaPorId(@PathVariable Long id) {
        Optional<Conta> conta = contaService.obterContaPorId(id);
        return conta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /contas/{id}/transferencia - Realizar uma transferência entre contas
    @PostMapping("/{id}/transferencia")
    public ResponseEntity<String> realizarTransferencia(@PathVariable Long id, @RequestParam Long contaDestinoId, @RequestParam BigDecimal valor) {
        boolean sucesso = contaService.realizarTransferencia(id, contaDestinoId, valor);
        if (sucesso) {
            return ResponseEntity.ok("Transferência realizada com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Transferência não realizada. Verifique os dados.");
        }
    }

    // GET /contas/{id}/saldo - Consultar saldo da conta
    @GetMapping("/{id}/saldo")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable Long id) {
        Optional<BigDecimal> saldo = contaService.consultarSaldo(id);
        return saldo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /contas/{id}/pix - Realizar um pagamento via Pix
    @PostMapping("/{id}/pix")
    public ResponseEntity<String> realizarPix(@PathVariable Long id, @RequestParam BigDecimal valor) {
        boolean sucesso = contaService.realizarPix(id, valor);
        if (sucesso) {
            return ResponseEntity.ok("Pagamento via Pix realizado com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Pagamento via Pix não realizado. Verifique os dados.");
        }
    }

    // POST /contas/{id}/deposito - Realizar um depósito na conta
    @PostMapping("/{id}/deposito")
    public ResponseEntity<String> realizarDeposito(@PathVariable Long id, @RequestParam BigDecimal valor) {
        boolean sucesso = contaService.realizarDeposito(id, valor);
        if (sucesso) {
            return ResponseEntity.ok("Depósito realizado com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Depósito não realizado. Verifique os dados.");
        }
    }

    // POST /contas/{id}/saque - Realizar um saque da conta
    @PostMapping("/{id}/saque")
    public ResponseEntity<String> realizarSaque(@PathVariable Long id, @RequestParam BigDecimal valor) {
        boolean sucesso = contaService.realizarSaque(id, valor);
        if (sucesso) {
            return ResponseEntity.ok("Saque realizado com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Saque não realizado. Verifique os dados.");
        }
    }

    // PUT /contas/{id}/manutencao - Aplicar taxa de manutenção mensal (para conta corrente)
    @PutMapping("/{id}/manutencao")
    public ResponseEntity<String> aplicarTaxaManutencao(@PathVariable Long id) {
        boolean sucesso = contaService.aplicarTaxaManutencao(id, "defaultReason");
        if (sucesso) {
            return ResponseEntity.ok("Taxa de manutenção aplicada com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Taxa de manutenção não aplicada. Verifique os dados.");
        }
    }

    // PUT /contas/{id}/rendimentos - Aplicar rendimentos (para conta poupança)
    @PutMapping("/{id}/rendimentos")
    public ResponseEntity<String> aplicarRendimentos(@PathVariable Long id) {
        boolean sucesso = contaService.aplicarRendimentos(id, "defaultReason");
        if (sucesso) {
            return ResponseEntity.ok("Rendimentos aplicados com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Rendimentos não aplicados. Verifique os dados.");
        }
    }
}