package br.com.cdb.bancodigitaljpa.endity;

import br.com.cdb.bancodigitaljpa.enuns.CategoriaCliente;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar vazio")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    @Column(name = "nome_cliente", nullable = false)
    private String nome;

    @NotNull(message = "O CPF é obrigatório")
    @Column(name = "cpf_cliente", unique = true, nullable = false)
    private Long cpf;

    @Past(message = "A data de nascimento deve ser no passado")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotBlank(message = "O endereço não pode estar vazio")
    @Size(max = 255, message = "O endereço deve ter no máximo 255 caracteres")
    @Column(name = "endereco_cliente", nullable = false)
    private String endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_cliente", nullable = false)
    private CategoriaCliente categoria;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Resolve a referência circular
    private List<Conta> contas = new ArrayList<>();

    // Construtor padrão
    public Cliente() {
    }

    // Construtor com argumentos
    public Cliente(String nome, Long cpf, LocalDate dataNascimento, String endereco, CategoriaCliente categoria) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
        this.categoria = categoria;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public CategoriaCliente getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaCliente categoria) {
        this.categoria = categoria;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;
    }
}