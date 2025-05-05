package com.bancodesangue.service;

import com.bancodesangue.dto.*;
import com.bancodesangue.exception.CpfDuplicadoException;
import com.bancodesangue.mapper.DoadorMapper;
import com.bancodesangue.model.Doador;
import com.bancodesangue.repository.DoadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoadorService {

    private final DoadorRepository doadorRepository;
    private final DoadorMapper doadorMapper;


    @Autowired
    public DoadorService(DoadorRepository doadorRepository, DoadorMapper doadorMapper) {
        this.doadorRepository = doadorRepository;
        this.doadorMapper = doadorMapper;
    }

    public List<DoadorDTO> salvarDoadores(List<DoadorDTO> doadoresDTO) {
        List<Doador> doadores = new ArrayList<>();

        for (DoadorDTO doadorDTO : doadoresDTO) {
            if (doadorRepository.existsByCpf(doadorDTO.getCpf())) {
                throw new CpfDuplicadoException(doadorDTO.getCpf());
            }

            Doador doador = doadorMapper.toEntity(doadorDTO);
            doadores.add(doador);
        }

        try {
            return doadorMapper.toDTOList(doadorRepository.saveAll(doadores));
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage() != null && e.getMessage().contains("cpf")) {
                throw new CpfDuplicadoException("Um ou mais CPFs já estão cadastrados no sistema");
            }
            throw e;
        }
    }
    public List<DoadorDTO> listarTodos() {
        return doadorMapper.toDTOList(doadorRepository.findAll());
    }

    public EstatisticasDTO gerarEstatisticas() {
        List<Doador> doadores = doadorRepository.findAll();

        return new EstatisticasDTO(
                calcularQuantidadePorEstado(doadores),
                calcularImcMedioPorFaixaEtaria(doadores),
                calcularPercentualObesos(doadores),
                calcularMediaIdadePorTipoSanguineo(doadores),
                calcularQuantidadeDoadoresPorReceptor(doadores)
        );
    }

    private List<EstadoQuantidadeDTO> calcularQuantidadePorEstado(List<Doador> doadores) {
        Map<String, Long> quantidadePorEstado = doadores.stream()
                .collect(Collectors.groupingBy(Doador::getEstado, Collectors.counting()));

        return quantidadePorEstado.entrySet().stream()
                .map(entry -> new EstadoQuantidadeDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(EstadoQuantidadeDTO::getEstado))
                .collect(Collectors.toList());
    }

    private List<ImcFaixaIdadeDTO> calcularImcMedioPorFaixaEtaria(List<Doador> doadores) {
        Map<Integer, List<Double>> imcPorFaixaEtaria = new HashMap<>();

        doadores.stream()
                .filter(d -> d.getFaixaEtaria() != null && d.getImc() != null)
                .forEach(d -> {
                    Integer faixa = d.getFaixaEtaria();
                    Double imc = d.getImc();

                    if (!imcPorFaixaEtaria.containsKey(faixa)) {
                        imcPorFaixaEtaria.put(faixa, new ArrayList<>());
                    }

                    imcPorFaixaEtaria.get(faixa).add(imc);
                });

        return imcPorFaixaEtaria.entrySet().stream()
                .map(entry -> {
                    Integer faixa = entry.getKey();
                    double imcMedio = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

                    imcMedio = Math.round(imcMedio * 100.0) / 100.0;

                    return new ImcFaixaIdadeDTO(faixa, faixa + 9, imcMedio);
                })
                .sorted(Comparator.comparing(ImcFaixaIdadeDTO::getFaixaInicial))
                .collect(Collectors.toList());
    }
    private PercentualObesosDTO calcularPercentualObesos(List<Doador> doadores) {
        List<Doador> homens = doadores.stream()
                .filter(d -> "Masculino".equalsIgnoreCase(d.getSexo()))
                .toList();

        List<Doador> mulheres = doadores.stream()
                .filter(d -> "Feminino".equalsIgnoreCase(d.getSexo()))
                .toList();

        long homensObesos = homens.stream().filter(Doador::isObeso).count();
        long mulheresObesas = mulheres.stream().filter(Doador::isObeso).count();

        double percentualHomens = homens.isEmpty() ? 0.0 : (double) homensObesos / homens.size() * 100;
        double percentualMulheres = mulheres.isEmpty() ? 0.0 : (double) mulheresObesas / mulheres.size() * 100;

        return new PercentualObesosDTO(percentualHomens, percentualMulheres);
    }

    private List<MediaIdadeTipoSanguineoDTO> calcularMediaIdadePorTipoSanguineo(List<Doador> doadores) {
        Map<String, List<Integer>> idadesPorTipoSanguineo = new HashMap<>();

        doadores.stream()
                .filter(d -> d.getTipoSanguineo() != null && d.getIdade() != null)
                .forEach(d -> {
                    String tipoSanguineo = d.getTipoSanguineo();
                    Integer idade = d.getIdade();

                    if (!idadesPorTipoSanguineo.containsKey(tipoSanguineo)) {
                        idadesPorTipoSanguineo.put(tipoSanguineo, new ArrayList<>());
                    }

                    idadesPorTipoSanguineo.get(tipoSanguineo).add(idade);
                });

        return idadesPorTipoSanguineo.entrySet().stream()
                .map(entry -> {
                    String tipoSanguineo = entry.getKey();
                    Double mediaIdade = entry.getValue().stream().mapToInt(Integer::intValue).average().orElse(0.0);

                    return new MediaIdadeTipoSanguineoDTO(tipoSanguineo, mediaIdade);
                })
                .sorted(Comparator.comparing(MediaIdadeTipoSanguineoDTO::getTipoSanguineo))
                .collect(Collectors.toList());
    }

    private Map<String, List<DoadorReceptorDTO>> calcularQuantidadeDoadoresPorReceptor(List<Doador> doadores) {
        Map<String, List<String>> tiposSanguineosDoadores = new HashMap<>();
        tiposSanguineosDoadores.put("A+", Arrays.asList("A+", "AB+"));
        tiposSanguineosDoadores.put("A-", Arrays.asList("A+", "A-", "AB+", "AB-"));
        tiposSanguineosDoadores.put("B+", Arrays.asList("B+", "AB+"));
        tiposSanguineosDoadores.put("B-", Arrays.asList("B+", "B-", "AB+", "AB-"));
        tiposSanguineosDoadores.put("AB+", List.of("AB+"));
        tiposSanguineosDoadores.put("AB-", Arrays.asList("AB+", "AB-"));
        tiposSanguineosDoadores.put("O+", Arrays.asList("A+", "B+", "O+", "AB+"));
        tiposSanguineosDoadores.put("O-", Arrays.asList("A+", "B+", "O+", "AB+", "A-", "B-", "O-", "AB-"));

        List<Doador> doadoresAptos = doadores.stream()
                .filter(Doador::podeDoar)
                .toList();

        Map<String, List<DoadorReceptorDTO>> doadoresPorReceptor = new HashMap<>();

        List<String> tiposSanguineosReceptores = Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

        for (String tipoReceptor : tiposSanguineosReceptores) {
            List<DoadorReceptorDTO> doadoresCompativeis = new ArrayList<>();

            for (Map.Entry<String, List<String>> entry : tiposSanguineosDoadores.entrySet()) {
                String tipoDoador = entry.getKey();
                List<String> receptoresCompativeis = entry.getValue();

                if (receptoresCompativeis.contains(tipoReceptor)) {
                    long quantidade = doadoresAptos.stream()
                            .filter(d -> tipoDoador.equals(d.getTipoSanguineo()))
                            .count();

                    doadoresCompativeis.add(new DoadorReceptorDTO(tipoDoador, quantidade));
                }
            }

            doadoresPorReceptor.put(tipoReceptor, doadoresCompativeis);
        }

        return doadoresPorReceptor;
    }
}