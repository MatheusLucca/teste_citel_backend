package com.bancodesangue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoQuantidadeDTO {
    private String estado;
    private Long quantidade;
}