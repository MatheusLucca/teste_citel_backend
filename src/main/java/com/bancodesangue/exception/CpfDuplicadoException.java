package com.bancodesangue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CpfDuplicadoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CpfDuplicadoException(String cpf) {
        super("CPF já cadastrado no sistema: " + cpf);
    }
}
