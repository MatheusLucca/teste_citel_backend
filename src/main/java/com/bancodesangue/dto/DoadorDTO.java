package com.bancodesangue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoadorDTO {

    private String nome;

    private String cpf;

    private String rg;

    private String data_nasc;

    private String sexo;

    private String mae;
    private String pai;

    private String email;

    private String cep;
    private String endereco;

    private Integer numero;

    private String bairro;
    private String cidade;
    private String estado;
    private String telefone_fixo;
    private String celular;


    private Double altura;


    private Double peso;

    private String tipo_sanguineo;

}
