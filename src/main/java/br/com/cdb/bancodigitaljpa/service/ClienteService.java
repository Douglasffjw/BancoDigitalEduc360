package br.com.cdb.bancodigitaljpa.service;

import br.com.cdb.bancodigitaljpa.endity.Cliente;
import br.com.cdb.bancodigitaljpa.endity.Conta;
import br.com.cdb.bancodigitaljpa.endity.ContaCorrente;
import br.com.cdb.bancodigitaljpa.endity.ContaPoupanca;
import br.com.cdb.bancodigitaljpa.enuns.CategoriaCliente;
import br.com.cdb.bancodigitaljpa.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Salvar ou atualizar um cliente
    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Buscar um cliente pelo ID
    public Optional<Cliente> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    // Listar todos os clientes
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    // Atualizar informações de um cliente
    public Optional<Cliente> updateCliente(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(clienteAtualizado.getNome());
            cliente.setCpf(clienteAtualizado.getCpf());
            cliente.setDataNascimento(clienteAtualizado.getDataNascimento());
            cliente.setEndereco(clienteAtualizado.getEndereco());
            cliente.setCategoria(clienteAtualizado.getCategoria());
            return clienteRepository.save(cliente);
        });
    }

    // Deletar um cliente pelo ID
    public boolean deleteCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Buscar clientes por categoria
    public List<Cliente> getClientesPorCategoria(CategoriaCliente categoria) {
        return clienteRepository.findByCategoria(categoria);
    }

    // Criar uma conta corrente para um cliente
    public Optional<ContaCorrente> criarContaCorrente(Long clienteId) {
        return clienteRepository.findById(clienteId).map(cliente -> {
            ContaCorrente contaCorrente = new ContaCorrente(cliente);
            cliente.getContas().add(contaCorrente);
            clienteRepository.save(cliente); // Salva o cliente com a nova conta
            return contaCorrente;
        });
    }

    // Criar uma conta poupança para um cliente
    public Optional<ContaPoupanca> criarContaPoupanca(Long clienteId) {
        return clienteRepository.findById(clienteId).map(cliente -> {
            ContaPoupanca contaPoupanca = new ContaPoupanca(cliente);
            cliente.getContas().add(contaPoupanca);
            clienteRepository.save(cliente); // Salva o cliente com a nova conta
            return contaPoupanca;
        });
    }

    // Listar todas as contas de um cliente
    public Optional<List<Conta>> listarContasPorCliente(Long clienteId) {
        return clienteRepository.findById(clienteId).map(Cliente::getContas);
    }
}