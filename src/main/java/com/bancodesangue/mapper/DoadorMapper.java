package com.bancodesangue.mapper;

import com.bancodesangue.dto.DoadorDTO;
import com.bancodesangue.model.Doador;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoadorMapper {

    public DoadorDTO toDTO(Doador doador) {
        if (doador == null) {
            return null;
        }

        DoadorDTO dto = new DoadorDTO();
        dto.setNome(doador.getNome());
        dto.setCpf(doador.getCpf());
        dto.setRg(doador.getRg());

        if (doador.getDataNascimento() != null) {
            String dataNasc = doador.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            dto.setData_nasc(dataNasc);
        }

        dto.setSexo(doador.getSexo());
        dto.setMae(doador.getMae());
        dto.setPai(doador.getPai());
        dto.setEmail(doador.getEmail());
        dto.setCep(doador.getCep());
        dto.setEndereco(doador.getEndereco());
        dto.setNumero(doador.getNumero());
        dto.setBairro(doador.getBairro());
        dto.setCidade(doador.getCidade());
        dto.setEstado(doador.getEstado());
        dto.setTelefone_fixo(doador.getTelefoneFixo());
        dto.setCelular(doador.getCelular());
        dto.setAltura(doador.getAltura());
        dto.setPeso(doador.getPeso());
        dto.setTipo_sanguineo(doador.getTipoSanguineo());

        return dto;
    }


    public Doador toEntity(DoadorDTO dto) {
        if (dto == null) {
            return null;
        }

        Doador doador = new Doador();
        doador.setNome(dto.getNome());
        doador.setCpf(dto.getCpf());
        doador.setRg(dto.getRg());
        doador.setDataNasc(dto.getData_nasc());
        doador.setSexo(dto.getSexo());
        doador.setMae(dto.getMae());
        doador.setPai(dto.getPai());
        doador.setEmail(dto.getEmail());
        doador.setCep(dto.getCep());
        doador.setEndereco(dto.getEndereco());
        doador.setNumero(dto.getNumero());
        doador.setBairro(dto.getBairro());
        doador.setCidade(dto.getCidade());
        doador.setEstado(dto.getEstado());
        doador.setTelefoneFixo(dto.getTelefone_fixo());
        doador.setCelular(dto.getCelular());
        doador.setAltura(dto.getAltura());
        doador.setPeso(dto.getPeso());
        doador.setTipoSanguineo(dto.getTipo_sanguineo());
        return doador;
    }

    public List<DoadorDTO> toDTOList(List<Doador> doadores) {
        if (doadores == null) {
            return null;
        }

        return doadores.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<Doador> toEntityList(List<DoadorDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}