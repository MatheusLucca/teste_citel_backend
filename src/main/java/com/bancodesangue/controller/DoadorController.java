package com.bancodesangue.controller;

import com.bancodesangue.dto.DoadorDTO;
import com.bancodesangue.dto.EstatisticasDTO;
import com.bancodesangue.service.DoadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/doadores")
public class DoadorController {

    private final DoadorService doadorService;

    @Autowired
    public DoadorController(DoadorService doadorService) {
        this.doadorService = doadorService;
    }

    @PostMapping
    public ResponseEntity<List<DoadorDTO>> cadastrarDoadores(@RequestBody List<DoadorDTO> doadoresDTO) {
        List<DoadorDTO> doadores = doadorService.salvarDoadores(doadoresDTO);
        return new ResponseEntity<>(doadores, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DoadorDTO>> listarDoadores() {
        List<DoadorDTO> doadores = doadorService.listarTodos();
        return new ResponseEntity<>(doadores, HttpStatus.OK);
    }

    @GetMapping("/estatisticas")
    public ResponseEntity<EstatisticasDTO> obterEstatisticas() {
        EstatisticasDTO estatisticas = doadorService.gerarEstatisticas();
        return new ResponseEntity<>(estatisticas, HttpStatus.OK);
    }
}