package com.bancodesangue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoadorReceptorDTO {
    private String tipoSanguineo;
    private Long quantidade;
}