package com.bancodesangue.dto;

import com.bancodesangue.util.FormatterUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaIdadeTipoSanguineoDTO {
    private String tipoSanguineo;
    private Double mediaIdade;

    public Double getMediaIdade() {
        return FormatterUtil.arredondarParaDuasCasas(mediaIdade);
    }
}