package br.com.cdb.bancodigitaljpa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.cdb.bancodigitaljpa.endity.Cliente;
import br.com.cdb.bancodigitaljpa.endity.Conta;
import br.com.cdb.bancodigitaljpa.endity.ContaCorrente;
import br.com.cdb.bancodigitaljpa.endity.ContaPoupanca;
import br.com.cdb.bancodigitaljpa.enuns.CategoriaCliente;
import br.com.cdb.bancodigitaljpa.service.ClienteService;

@RestController
@RequestMapping("/clientes") // Define o caminho base para os endpoints
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // POST /clientes - Criar um novo cliente
    @PostMapping
    public ResponseEntity<Cliente> addCliente(@RequestBody Cliente cliente) {
        Cliente clienteAdicionado = clienteService.salvarCliente(cliente);
        return new ResponseEntity<>(clienteAdicionado, HttpStatus.CREATED);
    }

    // GET /clientes - Listar todos os clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getClientes();
        return ResponseEntity.ok(clientes);
    }

    // GET /clientes/{id} - Obter detalhes de um cliente
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.getClienteById(id);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT /clientes/{id} - Atualizar informações de um cliente
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        Optional<Cliente> cliente = clienteService.updateCliente(id, clienteAtualizado);
        return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE /clientes/{id} - Remover um cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        boolean deleted = clienteService.deleteCliente(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /clientes/categoria/{categoria} - Buscar clientes por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Cliente>> getClientesPorCategoria(@PathVariable String categoria) {
        try {
            CategoriaCliente categoriaEnum = CategoriaCliente.valueOf(categoria.toUpperCase());
            List<Cliente> clientes = clienteService.getClientesPorCategoria(categoriaEnum);
            return ResponseEntity.ok(clientes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // Retorna 400 se a categoria for inválida
        }
    }

    // POST /clientes/{id}/contas/corrente - Criar uma conta corrente para um cliente
    @PostMapping("/{id}/contas/corrente")
    public ResponseEntity<Conta> criarContaCorrente(@PathVariable Long id) {
        Optional<ContaCorrente> conta = clienteService.criarContaCorrente(id);
        return conta.map(c -> ResponseEntity.ok((Conta) c)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /clientes/{id}/contas/poupanca - Criar uma conta poupança para um cliente
    @PostMapping("/{id}/contas/poupanca")
    public ResponseEntity<Conta> criarContaPoupanca(@PathVariable Long id) {
        Optional<ContaPoupanca> conta = clienteService.criarContaPoupanca(id);
        return conta.map(c -> ResponseEntity.ok((Conta) c)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET /clientes/{id}/contas - Listar todas as contas de um cliente
    @GetMapping("/{id}/contas")
    public ResponseEntity<List<Conta>> listarContas(@PathVariable Long id) {
        Optional<List<Conta>> contas = clienteService.listarContasPorCliente(id);
        return contas.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}