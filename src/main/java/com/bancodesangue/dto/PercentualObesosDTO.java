package com.bancodesangue.dto;

import com.bancodesangue.util.FormatterUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PercentualObesosDTO {
    private Double percentualHomens;
    private Double percentualMulheres;

    public Double getPercentualHomens() {
        return FormatterUtil.arredondarParaDuasCasas(percentualHomens);
    }

    public Double getPercentualMulheres() {
        return FormatterUtil.arredondarParaDuasCasas(percentualMulheres);
    }
}