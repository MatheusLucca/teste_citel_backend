package com.bancodesangue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstatisticasDTO {
    private List<EstadoQuantidadeDTO> quantidadePorEstado;
    private List<ImcFaixaIdadeDTO> imcMedioPorFaixaEtaria;
    private PercentualObesosDTO percentualObesos;
    private List<MediaIdadeTipoSanguineoDTO> mediaIdadePorTipoSanguineo;
    private Map<String, List<DoadorReceptorDTO>> quantidadeDoadoresPorReceptor;
}