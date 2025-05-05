package com.bancodesangue.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "doadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String rg;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private String sexo;

    @Column(nullable = false)
    private String mae;

    @Column
    private String pai;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(name = "telefone_fixo")
    private String telefoneFixo;

    @Column(nullable = false)
    private String celular;

    @Column(nullable = false)
    private Double altura;

    @Column(nullable = false)
    private Double peso;

    @Column(name = "tipo_sanguineo", nullable = false)
    private String tipoSanguineo;

    @Transient
    public Integer getIdade() {
        if (dataNascimento == null) {
            return null;
        }
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    @Transient
    public Double getImc() {
        if (altura == null || peso == null || altura == 0) {
            return null;
        }
        return peso / (altura * altura);
    }

    @Transient
    public boolean isObeso() {
        Double imc = getImc();
        return imc != null && imc > 30;
    }

    @Transient
    public boolean podeDoar() {
        Integer idade = getIdade();
        return idade != null && idade >= 16 && idade <= 69 && peso != null && peso > 50;
    }

    @Transient
    public Integer getFaixaEtaria() {
        Integer idade = getIdade();
        if (idade == null) {
            return null;
        }
        return (idade / 10) * 10;
    }

    public void setDataNasc(String dataNasc) {
        if (dataNasc != null && !dataNasc.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            this.dataNascimento = LocalDate.parse(dataNasc, formatter);
        }
    }
}