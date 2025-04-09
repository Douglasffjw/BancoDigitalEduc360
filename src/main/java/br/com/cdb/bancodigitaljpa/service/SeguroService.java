package br.com.cdb.bancodigitaljpa.service;

import br.com.cdb.bancodigitaljpa.endity.Cartao;
import br.com.cdb.bancodigitaljpa.endity.Seguro;
import br.com.cdb.bancodigitaljpa.repository.CartaoRepository;
import br.com.cdb.bancodigitaljpa.repository.SeguroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SeguroService {

    @Autowired
    private SeguroRepository seguroRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public Seguro contratarSeguro(Long cartaoId, String tipo, BigDecimal valorMensal, BigDecimal cobertura) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));
        Seguro seguro = new Seguro(tipo, valorMensal, cobertura, cartao);
        return seguroRepository.save(seguro);
    }

    public Optional<Seguro> obterSeguroPorId(Long id) {
        return seguroRepository.findById(id);
    }

    public List<Seguro> listarSeguros() {
        return seguroRepository.findAll();
    }

    public void cancelarSeguro(Long id) {
        Seguro seguro = seguroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seguro não encontrado."));
        seguro.setAtivo(false);
        seguroRepository.save(seguro);
    }
}