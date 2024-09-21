package com.gestao.biblioteca.domain.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEmprestimo {

    DISPONIVEL(1L),
    EMPRESTADO(2L);

    private final Long codigo;

    public static StatusEmprestimo fromCodigo(Long codigo) {

        for (StatusEmprestimo statusEmprestimo : StatusEmprestimo.values()) {

            if (statusEmprestimo.getCodigo().equals(codigo)) {

                return statusEmprestimo;
            }
        }

        throw new IllegalArgumentException("Código do status de empréstimo inválido: " + codigo);
    }

    public static boolean isEmprestado(Long statusEmprestimo) {

        return fromCodigo(statusEmprestimo).equals(EMPRESTADO);
    }
}
