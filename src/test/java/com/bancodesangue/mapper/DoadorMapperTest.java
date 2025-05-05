package com.bancodesangue.mapper;

import com.bancodesangue.dto.DoadorDTO;
import com.bancodesangue.model.Doador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DoadorMapperTest {

    private final DoadorMapper doadorMapper = new DoadorMapper();

    @Test
    @DisplayName("Deve mapear de Doador para DoadorDTO corretamente")
    void deveMapearDeDoadorParaDoadorDTOCorretamente() {
        Doador doador = new Doador();
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

        DoadorDTO dto = doadorMapper.toDTO(doador);

        assertNotNull(dto);
        assertEquals(doador.getNome(), dto.getNome());
        assertEquals(doador.getCpf(), dto.getCpf());
        assertEquals(doador.getRg(), dto.getRg());
        assertEquals("01/01/1990", dto.getData_nasc());
        assertEquals(doador.getSexo(), dto.getSexo());
        assertEquals(doador.getMae(), dto.getMae());
        assertEquals(doador.getPai(), dto.getPai());
        assertEquals(doador.getEmail(), dto.getEmail());
        assertEquals(doador.getCep(), dto.getCep());
        assertEquals(doador.getEndereco(), dto.getEndereco());
        assertEquals(doador.getNumero(), dto.getNumero());
        assertEquals(doador.getBairro(), dto.getBairro());
        assertEquals(doador.getCidade(), dto.getCidade());
        assertEquals(doador.getEstado(), dto.getEstado());
        assertEquals(doador.getTelefoneFixo(), dto.getTelefone_fixo());
        assertEquals(doador.getCelular(), dto.getCelular());
        assertEquals(doador.getAltura(), dto.getAltura());
        assertEquals(doador.getPeso(), dto.getPeso());
        assertEquals(doador.getTipoSanguineo(), dto.getTipo_sanguineo());
    }

    @Test
    @DisplayName("Deve mapear de DoadorDTO para Doador corretamente")
    void deveMapearDeDoadorDTOParaDoadorCorretamente() {
        DoadorDTO dto = new DoadorDTO();
        dto.setNome("João Silva");
        dto.setCpf("123.456.789-00");
        dto.setRg("12345678");
        dto.setData_nasc("01/01/1990");
        dto.setSexo("Masculino");
        dto.setMae("Maria Silva");
        dto.setPai("José Silva");
        dto.setEmail("joao@example.com");
        dto.setCep("12345-678");
        dto.setEndereco("Rua Test");
        dto.setNumero(123);
        dto.setBairro("Centro");
        dto.setCidade("São Paulo");
        dto.setEstado("SP");
        dto.setTelefone_fixo("1122334455");
        dto.setCelular("11987654321");
        dto.setAltura(1.75);
        dto.setPeso(70.0);
        dto.setTipo_sanguineo("A+");

        Doador doador = doadorMapper.toEntity(dto);

        assertNotNull(doador);
        assertEquals(dto.getNome(), doador.getNome());
        assertEquals(dto.getCpf(), doador.getCpf());
        assertEquals(dto.getRg(), doador.getRg());
        assertEquals(LocalDate.of(1990, 1, 1), doador.getDataNascimento());
        assertEquals(dto.getSexo(), doador.getSexo());
        assertEquals(dto.getMae(), doador.getMae());
        assertEquals(dto.getPai(), doador.getPai());
        assertEquals(dto.getEmail(), doador.getEmail());
        assertEquals(dto.getCep(), doador.getCep());
        assertEquals(dto.getEndereco(), doador.getEndereco());
        assertEquals(dto.getNumero(), doador.getNumero());
        assertEquals(dto.getBairro(), doador.getBairro());
        assertEquals(dto.getCidade(), doador.getCidade());
        assertEquals(dto.getEstado(), doador.getEstado());
        assertEquals(dto.getTelefone_fixo(), doador.getTelefoneFixo());
        assertEquals(dto.getCelular(), doador.getCelular());
        assertEquals(dto.getAltura(), doador.getAltura());
        assertEquals(dto.getPeso(), doador.getPeso());
        assertEquals(dto.getTipo_sanguineo(), doador.getTipoSanguineo());
    }

    @Test
    @DisplayName("Deve retornar null quando Doador for null")
    void deveRetornarNullQuandoDoadorForNull() {
        assertNull(doadorMapper.toDTO(null));
    }

    @Test
    @DisplayName("Deve retornar null quando DoadorDTO for null")
    void deveRetornarNullQuandoDoadorDTOForNull() {
        assertNull(doadorMapper.toEntity(null));
    }

    @Test
    @DisplayName("Deve mapear lista de Doador para lista de DoadorDTO corretamente")
    void deveMapearListaDeDoadorParaListaDeDoadorDTOCorretamente() {
        Doador doador1 = new Doador();
        doador1.setId(1L);
        doador1.setNome("João Silva");
        doador1.setCpf("123.456.789-00");
        doador1.setDataNascimento(LocalDate.of(1990, 1, 1));

        Doador doador2 = new Doador();
        doador2.setId(2L);
        doador2.setNome("Maria Santos");
        doador2.setCpf("987.654.321-00");
        doador2.setDataNascimento(LocalDate.of(1985, 5, 10));

        List<Doador> doadores = Arrays.asList(doador1, doador2);

        List<DoadorDTO> dtos = doadorMapper.toDTOList(doadores);

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals(doador1.getNome(), dtos.get(0).getNome());
        assertEquals(doador1.getCpf(), dtos.get(0).getCpf());
        assertEquals("01/01/1990", dtos.get(0).getData_nasc());
        assertEquals(doador2.getNome(), dtos.get(1).getNome());
        assertEquals(doador2.getCpf(), dtos.get(1).getCpf());
        assertEquals("10/05/1985", dtos.get(1).getData_nasc());
    }

    @Test
    @DisplayName("Deve mapear lista de DoadorDTO para lista de Doador corretamente")
    void deveMapearListaDeDoadorDTOParaListaDeDoadorCorretamente() {
        DoadorDTO dto1 = new DoadorDTO();
        dto1.setNome("João Silva");
        dto1.setCpf("123.456.789-00");
        dto1.setData_nasc("01/01/1990");

        DoadorDTO dto2 = new DoadorDTO();
        dto2.setNome("Maria Santos");
        dto2.setCpf("987.654.321-00");
        dto2.setData_nasc("10/05/1985");

        List<DoadorDTO> dtos = Arrays.asList(dto1, dto2);

        List<Doador> doadores = doadorMapper.toEntityList(dtos);

        assertNotNull(doadores);
        assertEquals(2, doadores.size());
        assertEquals(dto1.getNome(), doadores.get(0).getNome());
        assertEquals(dto1.getCpf(), doadores.get(0).getCpf());
        assertEquals(LocalDate.of(1990, 1, 1), doadores.get(0).getDataNascimento());
        assertEquals(dto2.getNome(), doadores.get(1).getNome());
        assertEquals(dto2.getCpf(), doadores.get(1).getCpf());
        assertEquals(LocalDate.of(1985, 5, 10), doadores.get(1).getDataNascimento());
    }

    @Test
    @DisplayName("Deve retornar null quando lista de Doador for null")
    void deveRetornarNullQuandoListaDeDoadorForNull() {
        assertNull(doadorMapper.toDTOList(null));
    }

    @Test
    @DisplayName("Deve retornar null quando lista de DoadorDTO for null")
    void deveRetornarNullQuandoListaDeDoadorDTOForNull() {
        assertNull(doadorMapper.toEntityList(null));
    }

    @Test
    @DisplayName("Deve formatar data de nascimento corretamente no DTO")
    void deveFormatarDataNascimentoCorretamenteNoDTO() {
        Doador doador = new Doador();
        doador.setDataNascimento(LocalDate.of(1990, 1, 1));

        DoadorDTO dto = doadorMapper.toDTO(doador);

        assertEquals("01/01/1990", dto.getData_nasc());
    }

    @Test
    @DisplayName("Deve manter data_nasc null no DTO quando dataNascimento for null")
    void deveManterDataNascNullNoDTOQuandoDataNascimentoForNull() {
        Doador doador = new Doador();
        doador.setDataNascimento(null);

        DoadorDTO dto = doadorMapper.toDTO(doador);

        assertNull(dto.getData_nasc());
    }
}