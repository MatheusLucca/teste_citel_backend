package com.bancodesangue.service;

import com.bancodesangue.dto.*;
import com.bancodesangue.exception.CpfDuplicadoException;
import com.bancodesangue.mapper.DoadorMapper;
import com.bancodesangue.model.Doador;
import com.bancodesangue.repository.DoadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoadorServiceTest {

    @Mock
    private DoadorRepository doadorRepository;

    @Mock
    private DoadorMapper doadorMapper;

    @InjectMocks
    private DoadorService doadorService;

    private DoadorDTO doadorDTO;
    private Doador doador;
    private List<DoadorDTO> doadoresDTO;
    private List<Doador> doadores;

    @BeforeEach
    void setUp() {
        doadorDTO = new DoadorDTO();
        doadorDTO.setNome("João Silva");
        doadorDTO.setCpf("123.456.789-00");
        doadorDTO.setRg("12345678");
        doadorDTO.setData_nasc("01/01/1990");
        doadorDTO.setSexo("Masculino");
        doadorDTO.setMae("Maria Silva");
        doadorDTO.setPai("José Silva");
        doadorDTO.setEmail("joao@example.com");
        doadorDTO.setCep("12345-678");
        doadorDTO.setEndereco("Rua Test");
        doadorDTO.setNumero(123);
        doadorDTO.setBairro("Centro");
        doadorDTO.setCidade("São Paulo");
        doadorDTO.setEstado("SP");
        doadorDTO.setTelefone_fixo("1122334455");
        doadorDTO.setCelular("11987654321");
        doadorDTO.setAltura(1.75);
        doadorDTO.setPeso(70.0);
        doadorDTO.setTipo_sanguineo("A+");

        doador = new Doador();
        doador.setId(1L);
        doador.setNome("João Silva");
        doador.setCpf("123.456.789-00");
        doador.setRg("12345678");
        doador.setDataNascimento(LocalDate.of(1990, 1, 1));
        doador.setSexo("Masculino");
        doador.setMae("Maria Silva");
        doador.setPai("José Silva");
        doador.setEmail("joao@example.com");
        doador.setCep("12345-678");
        doador.setEndereco("Rua Test");
        doador.setNumero(123);
        doador.setBairro("Centro");
        doador.setCidade("São Paulo");
        doador.setEstado("SP");
        doador.setTelefoneFixo("1122334455");
        doador.setCelular("11987654321");
        doador.setAltura(1.75);
        doador.setPeso(70.0);
        doador.setTipoSanguineo("A+");

        doadoresDTO = List.of(doadorDTO);
        doadores = List.of(doador);
    }

    @Test
    @DisplayName("Deve salvar doadores com sucesso")
    void deveSalvarDoadoresComSucesso() {
        when(doadorRepository.existsByCpf(anyString())).thenReturn(false);
        when(doadorMapper.toEntity(any(DoadorDTO.class))).thenReturn(doador);
        when(doadorRepository.saveAll(anyList())).thenReturn(doadores);
        when(doadorMapper.toDTOList(anyList())).thenReturn(doadoresDTO);

        List<DoadorDTO> resultado = doadorService.salvarDoadores(doadoresDTO);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(doadorDTO.getNome(), resultado.get(0).getNome());
        assertEquals(doadorDTO.getCpf(), resultado.get(0).getCpf());

        verify(doadorRepository).existsByCpf(doadorDTO.getCpf());
        verify(doadorMapper).toEntity(doadorDTO);
        verify(doadorRepository).saveAll(anyList());
        verify(doadorMapper).toDTOList(doadores);
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF já existir")
    void deveLancarExcecaoQuandoCpfJaExistir() {
        when(doadorRepository.existsByCpf(anyString())).thenReturn(true);

        assertThrows(CpfDuplicadoException.class, () -> {
            doadorService.salvarDoadores(doadoresDTO);
        });

        verify(doadorRepository).existsByCpf(doadorDTO.getCpf());
        verify(doadorMapper, never()).toEntity(any(DoadorDTO.class));
        verify(doadorRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("Deve lançar exceção quando ocorrer violação de integridade")
    void deveLancarExcecaoQuandoOcorrerViolacaoDeIntegridade() {
        when(doadorRepository.existsByCpf(anyString())).thenReturn(false);
        when(doadorMapper.toEntity(any(DoadorDTO.class))).thenReturn(doador);
        when(doadorRepository.saveAll(anyList())).thenThrow(
                new DataIntegrityViolationException("Constraint violation: cpf_unique")
        );

        assertThrows(CpfDuplicadoException.class, () -> {
            doadorService.salvarDoadores(doadoresDTO);
        });

        verify(doadorRepository).existsByCpf(doadorDTO.getCpf());
        verify(doadorMapper).toEntity(doadorDTO);
        verify(doadorRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("Deve listar todos os doadores")
    void deveListarTodosOsDoadores() {
        when(doadorRepository.findAll()).thenReturn(doadores);
        when(doadorMapper.toDTOList(anyList())).thenReturn(doadoresDTO);

        List<DoadorDTO> resultado = doadorService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(doadorDTO.getNome(), resultado.get(0).getNome());

        verify(doadorRepository).findAll();
        verify(doadorMapper).toDTOList(doadores);
    }

    @Test
    @DisplayName("Deve gerar estatísticas corretamente")
    void deveGerarEstatisticasCorretamente() {
        Doador doador1 = criarDoador(1L, "João", "Masculino", "A+", 25, 1.75, 70.0, "SP");
        Doador doador2 = criarDoador(2L, "Maria", "Feminino", "O-", 35, 1.65, 60.0, "RJ");
        Doador doador3 = criarDoador(3L, "Pedro", "Masculino", "B+", 45, 1.80, 100.0, "SP"); // IMC > 30, obeso
        Doador doador4 = criarDoador(4L, "Ana", "Feminino", "AB+", 55, 1.60, 80.0, "MG"); // IMC > 30, obesa

        List<Doador> todosDoadores = Arrays.asList(doador1, doador2, doador3, doador4);
        when(doadorRepository.findAll()).thenReturn(todosDoadores);

        EstatisticasDTO estatisticas = doadorService.gerarEstatisticas();

        assertNotNull(estatisticas);

        List<EstadoQuantidadeDTO> quantidadePorEstado = estatisticas.getQuantidadePorEstado();
        assertEquals(3, quantidadePorEstado.size());

        List<ImcFaixaIdadeDTO> imcPorFaixaEtaria = estatisticas.getImcMedioPorFaixaEtaria();
        assertNotNull(imcPorFaixaEtaria);

        PercentualObesosDTO percentualObesos = estatisticas.getPercentualObesos();
        assertNotNull(percentualObesos);
        assertEquals(50.0, percentualObesos.getPercentualHomens(), 0.1);
        assertEquals(50.0, percentualObesos.getPercentualMulheres(), 0.1);

        List<MediaIdadeTipoSanguineoDTO> mediaIdadePorTipoSanguineo = estatisticas.getMediaIdadePorTipoSanguineo();
        assertNotNull(mediaIdadePorTipoSanguineo);
        assertEquals(4, mediaIdadePorTipoSanguineo.size());

        Map<String, List<DoadorReceptorDTO>> doadoresPorReceptor = estatisticas.getQuantidadeDoadoresPorReceptor();
        assertNotNull(doadoresPorReceptor);
        assertEquals(8, doadoresPorReceptor.size());

        verify(doadorRepository).findAll();
    }

    @Test
    @DisplayName("Deve calcular corretamente a quantidade de doadores por estado")
    void deveCalcularCorretamenteQuantidadeDoadoresPorEstado() {

        Doador doador1 = criarDoador(1L, "João", "Masculino", "A+", 25, 1.75, 70.0, "SP");
        Doador doador2 = criarDoador(2L, "Maria", "Feminino", "O-", 35, 1.65, 60.0, "RJ");
        Doador doador3 = criarDoador(3L, "Pedro", "Masculino", "B+", 45, 1.80, 90.0, "SP");

        List<Doador> todosDoadores = Arrays.asList(doador1, doador2, doador3);
        when(doadorRepository.findAll()).thenReturn(todosDoadores);

        EstatisticasDTO estatisticas = doadorService.gerarEstatisticas();

        List<EstadoQuantidadeDTO> quantidadePorEstado = estatisticas.getQuantidadePorEstado();
        assertEquals(2, quantidadePorEstado.size());

        boolean encontrouSP = false;
        boolean encontrouRJ = false;

        for (EstadoQuantidadeDTO estado : quantidadePorEstado) {
            if ("SP".equals(estado.getEstado())) {
                assertEquals(2, estado.getQuantidade());
                encontrouSP = true;
            } else if ("RJ".equals(estado.getEstado())) {
                assertEquals(1, estado.getQuantidade());
                encontrouRJ = true;
            }
        }

        assertTrue(encontrouSP, "Deveria ter encontrado o estado SP");
        assertTrue(encontrouRJ, "Deveria ter encontrado o estado RJ");
    }

    @Test
    @DisplayName("Deve calcular corretamente o IMC médio por faixa etária")
    void deveCalcularCorretamenteImcMedioPorFaixaEtaria() {

        Doador doador1 = criarDoador(1L, "João", "Masculino", "A+", 20, 1.80, 72.0, "SP"); // IMC 22.22
        Doador doador2 = criarDoador(2L, "Maria", "Feminino", "O-", 25, 1.65, 60.0, "RJ"); // IMC 22.04

        Doador doador3 = criarDoador(3L, "Pedro", "Masculino", "B+", 30, 1.70, 80.0, "SP"); // IMC 27.68
        Doador doador4 = criarDoador(4L, "Ana", "Feminino", "AB+", 35, 1.60, 70.0, "MG"); // IMC 27.34

        List<Doador> todosDoadores = Arrays.asList(doador1, doador2, doador3, doador4);
        when(doadorRepository.findAll()).thenReturn(todosDoadores);

        EstatisticasDTO estatisticas = doadorService.gerarEstatisticas();

        List<ImcFaixaIdadeDTO> imcPorFaixaEtaria = estatisticas.getImcMedioPorFaixaEtaria();
        assertEquals(2, imcPorFaixaEtaria.size());

        boolean encontrou20 = false;
        boolean encontrou30 = false;

        for (ImcFaixaIdadeDTO faixa : imcPorFaixaEtaria) {
            if (faixa.getFaixaInicial() == 20) {
                assertEquals(20, faixa.getFaixaInicial());
                assertEquals(29, faixa.getFaixaFinal());
                assertEquals(22.13, faixa.getImcMedio(), 0.01); // Média de 22.22 e 22.04
                encontrou20 = true;
            } else if (faixa.getFaixaInicial() == 30) {
                assertEquals(30, faixa.getFaixaInicial());
                assertEquals(39, faixa.getFaixaFinal());
                assertEquals(27.51, faixa.getImcMedio(), 0.01); // Média de 27.68 e 27.34
                encontrou30 = true;
            }
        }

        assertTrue(encontrou20, "Deveria ter encontrado a faixa etária 20-29");
        assertTrue(encontrou30, "Deveria ter encontrado a faixa etária 30-39");
    }

    @Test
    @DisplayName("Deve calcular corretamente o percentual de obesos")
    void deveCalcularCorretamentePercentualObesos() {

        Doador doador1 = criarDoador(1L, "João", "Masculino", "A+", 25, 1.75, 70.0, "SP"); // IMC 22.86, não obeso
        Doador doador2 = criarDoador(2L, "Pedro", "Masculino", "B+", 35, 1.70, 100.0, "SP"); // IMC 34.60, obeso

        Doador doador3 = criarDoador(3L, "Maria", "Feminino", "O-", 30, 1.65, 60.0, "RJ"); // IMC 22.04, não obesa
        Doador doador4 = criarDoador(4L, "Ana", "Feminino", "AB+", 40, 1.60, 85.0, "MG"); // IMC 33.20, obesa
        Doador doador5 = criarDoador(5L, "Lúcia", "Feminino", "O+", 50, 1.70, 65.0, "RS"); // IMC 22.49, não obesa

        List<Doador> todosDoadores = Arrays.asList(doador1, doador2, doador3, doador4, doador5);
        when(doadorRepository.findAll()).thenReturn(todosDoadores);

        EstatisticasDTO estatisticas = doadorService.gerarEstatisticas();

        PercentualObesosDTO percentualObesos = estatisticas.getPercentualObesos();
        assertEquals(50.0, percentualObesos.getPercentualHomens(), 0.01); // 1 de 2 homens é obeso (50%)
        assertEquals(33.33, percentualObesos.getPercentualMulheres(), 0.01); // 1 de 3 mulheres é obesa (33.33%)
    }

    @Test
    @DisplayName("Deve calcular corretamente a média de idade por tipo sanguíneo")
    void deveCalcularCorretamenteMediaIdadePorTipoSanguineo() {
        Doador doador1 = criarDoador(1L, "João", "Masculino", "A+", 20, 1.75, 70.0, "SP");
        Doador doador2 = criarDoador(2L, "Maria", "Feminino", "A+", 30, 1.65, 60.0, "RJ");

        Doador doador3 = criarDoador(3L, "Pedro", "Masculino", "O-", 40, 1.80, 80.0, "SP");
        Doador doador4 = criarDoador(4L, "Ana", "Feminino", "O-", 50, 1.60, 70.0, "MG");

        Doador doador5 = criarDoador(5L, "Carlos", "Masculino", "B+", 60, 1.70, 75.0, "RS");

        List<Doador> todosDoadores = Arrays.asList(doador1, doador2, doador3, doador4, doador5);
        when(doadorRepository.findAll()).thenReturn(todosDoadores);

        EstatisticasDTO estatisticas = doadorService.gerarEstatisticas();

        List<MediaIdadeTipoSanguineoDTO> mediaIdadePorTipoSanguineo = estatisticas.getMediaIdadePorTipoSanguineo();
        assertEquals(3, mediaIdadePorTipoSanguineo.size());

        boolean encontrouAPositivo = false;
        boolean encontrouONegativo = false;
        boolean encontrouBPositivo = false;

        for (MediaIdadeTipoSanguineoDTO tipo : mediaIdadePorTipoSanguineo) {
            if ("A+".equals(tipo.getTipoSanguineo())) {
                assertEquals(25.0, tipo.getMediaIdade(), 0.01);
                encontrouAPositivo = true;
            } else if ("O-".equals(tipo.getTipoSanguineo())) {
                assertEquals(45.0, tipo.getMediaIdade(), 0.01);
                encontrouONegativo = true;
            } else if ("B+".equals(tipo.getTipoSanguineo())) {
                assertEquals(60.0, tipo.getMediaIdade(), 0.01);
                encontrouBPositivo = true;
            }
        }

        assertTrue(encontrouAPositivo, "Deveria ter encontrado o tipo sanguíneo A+");
        assertTrue(encontrouONegativo, "Deveria ter encontrado o tipo sanguíneo O-");
        assertTrue(encontrouBPositivo, "Deveria ter encontrado o tipo sanguíneo B+");
    }

    @Test
    @DisplayName("Deve calcular corretamente os doadores por receptor")
    void deveCalcularCorretamenteQuantidadeDoadoresPorReceptor() {
        Doador doador1 = criarDoador(1L, "João", "Masculino", "A+", 25, 1.75, 70.0, "SP");
        Doador doador2 = criarDoador(2L, "Maria", "Feminino", "O-", 35, 1.65, 60.0, "RJ");
        Doador doador3 = criarDoador(3L, "Pedro", "Masculino", "B-", 45, 1.80, 80.0, "SP");

        Doador doador4 = criarDoador(4L, "Lucas", "Masculino", "AB+", 15, 1.60, 55.0, "MG");

        Doador doador5 = criarDoador(5L, "Carlos", "Masculino", "O+", 70, 1.70, 75.0, "RS");

        Doador doador6 = criarDoador(6L, "Ana", "Feminino", "A-", 30, 1.60, 50.0, "SC");

        List<Doador> todosDoadores = Arrays.asList(doador1, doador2, doador3, doador4, doador5, doador6);
        when(doadorRepository.findAll()).thenReturn(todosDoadores);

        EstatisticasDTO estatisticas = doadorService.gerarEstatisticas();

        Map<String, List<DoadorReceptorDTO>> doadoresPorReceptor = estatisticas.getQuantidadeDoadoresPorReceptor();
        assertEquals(8, doadoresPorReceptor.size());

        List<DoadorReceptorDTO> doadoresParaAPositivo = doadoresPorReceptor.get("A+");
        assertNotNull(doadoresParaAPositivo);

        long totalDoadoresParaAPositivo = 0;
        for (DoadorReceptorDTO doadorReceptor : doadoresParaAPositivo) {
            if ("A+".equals(doadorReceptor.getTipoSanguineo())) {
                assertEquals(1, doadorReceptor.getQuantidade());
            } else if ("O-".equals(doadorReceptor.getTipoSanguineo())) {
                assertEquals(1, doadorReceptor.getQuantidade());
            }
            totalDoadoresParaAPositivo += doadorReceptor.getQuantidade();
        }

        assertEquals(2, totalDoadoresParaAPositivo);
    }

    private Doador criarDoador(Long id, String nome, String sexo, String tipoSanguineo,
                               int idade, double altura, double peso, String estado) {
        Doador doador = new Doador();
        doador.setId(id);
        doador.setNome(nome);
        doador.setCpf(id + "23.456.789-00");
        doador.setRg(id + "2345678");
        doador.setDataNascimento(LocalDate.now().minusYears(idade));
        doador.setSexo(sexo);
        doador.setMae("Mãe do " + nome);
        doador.setPai("Pai do " + nome);
        doador.setEmail(nome.toLowerCase() + "@example.com");
        doador.setCep("12345-678");
        doador.setEndereco("Rua " + nome);
        doador.setNumero(123);
        doador.setBairro("Centro");
        doador.setCidade("Cidade " + id);
        doador.setEstado(estado);
        doador.setTelefoneFixo("11" + id + "334455");
        doador.setCelular("11" + id + "87654321");
        doador.setAltura(altura);
        doador.setPeso(peso);
        doador.setTipoSanguineo(tipoSanguineo);
        return doador;
    }
}