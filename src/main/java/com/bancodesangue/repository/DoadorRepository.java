package com.bancodesangue.repository;

import com.bancodesangue.model.Doador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DoadorRepository extends JpaRepository<Doador, Long> {
    boolean existsByCpf(String cpf);
}