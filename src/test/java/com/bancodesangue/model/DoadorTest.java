package com.bancodesangue.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DoadorTest {

    @Test
    @DisplayName("Deve calcular idade corretamente")
    void deveCalcularIdadeCorretamente() {
        Doador doador = new Doador();
        int anosAtras = 30;
        doador.setDataNascimento(LocalDate.now().minusYears(anosAtras));

        Integer idade = doador.getIdade();

        assertNotNull(idade);
        assertEquals(anosAtras, idade);
    }

    @Test
    @DisplayName("Deve retornar null para idade quando data de nascimento for null")
    void deveRetornarNullParaIdadeQuandoDataNascimentoForNull() {
        Doador doador = new Doador();
        doador.setDataNascimento(null);

        Integer idade = doador.getIdade();

        assertNull(idade);
    }

    @Test
    @DisplayName("Deve calcular IMC corretamente")
    void deveCalcularImcCorretamente() {
        Doador doador = new Doador();
        doador.setAltura(1.75);
        doador.setPeso(70.0);

        double imcEsperado = 70.0 / (1.75 * 1.75);

        Double imc = doador.getImc();

        assertNotNull(imc);
        assertEquals(imcEsperado, imc, 0.001);
    }

    @Test
    @DisplayName("Deve retornar null para IMC quando altura ou peso forem null")
    void deveRetornarNullParaImcQuandoAlturaPesoForemNull() {
        Doador doador1 = new Doador();
        doador1.setAltura(null);
        doador1.setPeso(70.0);

        Doador doador2 = new Doador();
        doador2.setAltura(1.75);
        doador2.setPeso(null);

        Doador doador3 = new Doador();
        doador3.setAltura(null);
        doador3.setPeso(null);

        assertNull(doador1.getImc());
        assertNull(doador2.getImc());
        assertNull(doador3.getImc());
    }

    @Test
    @DisplayName("Deve retornar null para IMC quando altura for zero")
    void deveRetornarNullParaImcQuandoAlturaForZero() {
        Doador doador = new Doador();
        doador.setAltura(0.0);
        doador.setPeso(70.0);

        Double imc = doador.getImc();

        assertNull(imc);
    }

    @Test
    @DisplayName("Deve identificar obesidade corretamente")
    void deveIdentificarObesidadeCorretamente() {

        Doador doadorNaoObeso = new Doador();
        doadorNaoObeso.setAltura(1.75);
        doadorNaoObeso.setPeso(70.0);

        Doador doadorObeso = new Doador();
        doadorObeso.setAltura(1.75);
        doadorObeso.setPeso(93.0);

        Doador doadorSemDados = new Doador();
        doadorSemDados.setAltura(null);
        doadorSemDados.setPeso(70.0);

        assertFalse(doadorNaoObeso.isObeso());
        assertTrue(doadorObeso.isObeso());
        assertFalse(doadorSemDados.isObeso());
    }

    @Test
    @DisplayName("Deve verificar elegibilidade para doação corretamente")
    void deveVerificarElegibilidadeParaDoacaoCorretamente() {

        Doador doadorApto = new Doador();
        doadorApto.setDataNascimento(LocalDate.now().minusYears(30));
        doadorApto.setPeso(70.0);

        Doador doadorMuitoJovem = new Doador();
        doadorMuitoJovem.setDataNascimento(LocalDate.now().minusYears(15));
        doadorMuitoJovem.setPeso(70.0);

        Doador doadorMuitoIdoso = new Doador();
        doadorMuitoIdoso.setDataNascimento(LocalDate.now().minusYears(70));
        doadorMuitoIdoso.setPeso(70.0);

        Doador doadorMuitoLeve = new Doador();
        doadorMuitoLeve.setDataNascimento(LocalDate.now().minusYears(30));
        doadorMuitoLeve.setPeso(50.0);

        Doador doadorSemDados = new Doador();
        doadorSemDados.setDataNascimento(null);
        doadorSemDados.setPeso(null);

        assertTrue(doadorApto.podeDoar());
        assertFalse(doadorMuitoJovem.podeDoar());
        assertFalse(doadorMuitoIdoso.podeDoar());
        assertFalse(doadorMuitoLeve.podeDoar());
        assertFalse(doadorSemDados.podeDoar());
    }

    @Test
    @DisplayName("Deve calcular faixa etária corretamente")
    void deveCalcularFaixaEtariaCorretamente() {
        Doador doador1 = new Doador();
        doador1.setDataNascimento(LocalDate.now().minusYears(25));

        Doador doador2 = new Doador();
        doador2.setDataNascimento(LocalDate.now().minusYears(30));

        Doador doador3 = new Doador();
        doador3.setDataNascimento(LocalDate.now().minusYears(42));

        Doador doadorSemDados = new Doador();
        doadorSemDados.setDataNascimento(null);

        assertEquals(20, doador1.getFaixaEtaria());
        assertEquals(30, doador2.getFaixaEtaria());
        assertEquals(40, doador3.getFaixaEtaria());
        assertNull(doadorSemDados.getFaixaEtaria());
    }

    @Test
    @DisplayName("Deve converter string de data para LocalDate corretamente")
    void deveConverterStringDeDataParaLocalDateCorretamente() {
        Doador doador = new Doador();
        String dataString = "01/01/1990";

        doador.setDataNasc(dataString);

        assertNotNull(doador.getDataNascimento());
        assertEquals(1990, doador.getDataNascimento().getYear());
        assertEquals(1, doador.getDataNascimento().getMonthValue());
        assertEquals(1, doador.getDataNascimento().getDayOfMonth());
    }

    @Test
    @DisplayName("Não deve alterar dataNascimento quando string for null ou vazia")
    void naoDeveAlterarDataNascimentoQuandoStringForNullOuVazia() {
        Doador doador1 = new Doador();
        Doador doador2 = new Doador();

        doador1.setDataNasc(null);
        doador2.setDataNasc("");

        assertNull(doador1.getDataNascimento());
        assertNull(doador2.getDataNascimento());
    }
}