package com.bancodesangue.dto;

import com.bancodesangue.util.FormatterUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImcFaixaIdadeDTO {
    private Integer faixaInicial;
    private Integer faixaFinal;
    private Double imcMedio;
    
    public Double getImcMedio() {
        return FormatterUtil.arredondarParaDuasCasas(imcMedio);
    }
}