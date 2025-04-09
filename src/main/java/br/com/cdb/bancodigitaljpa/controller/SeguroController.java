package br.com.cdb.bancodigitaljpa.controller;

import br.com.cdb.bancodigitaljpa.endity.Seguro;
import br.com.cdb.bancodigitaljpa.service.SeguroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/seguros")
public class SeguroController {

    @Autowired
    private SeguroService seguroService;

    // POST /seguros - Contratar um seguro
    @PostMapping
    public ResponseEntity<Seguro> contratarSeguro(@RequestParam Long cartaoId,
                                                   @RequestParam String tipo,
                                                   @RequestParam BigDecimal valorMensal,
                                                   @RequestParam BigDecimal cobertura) {
        Seguro seguro = seguroService.contratarSeguro(cartaoId, tipo, valorMensal, cobertura);
        return ResponseEntity.ok(seguro);
    }

    // GET /seguros/{id} - Obter detalhes de uma apólice de seguro
    @GetMapping("/{id}")
    public ResponseEntity<Seguro> obterSeguroPorId(@PathVariable Long id) {
        return seguroService.obterSeguroPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /seguros - Listar todos os seguros disponíveis
    @GetMapping
    public ResponseEntity<List<Seguro>> listarSeguros() {
        return ResponseEntity.ok(seguroService.listarSeguros());
    }

    // PUT /seguros/{id}/cancelar - Cancelar uma apólice de seguro
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<String> cancelarSeguro(@PathVariable Long id) {
        seguroService.cancelarSeguro(id);
        return ResponseEntity.ok("Seguro cancelado com sucesso.");
    }
}