package com.bancodesangue.util;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class FormatterUtil {

    public static Double arredondarParaDuasCasas(Double valor) {
        if (valor == null) return null;
        return BigDecimal.valueOf(valor)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}