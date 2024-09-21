package com.gestao.biblioteca.domain.enumerations;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Categorias {

    FICCAO(1L),
    NAO_FICCAO(2L),
    INFANTOJUVENIL(3L),
    ACADEMICOS_E_TECNICOS(4L),
    POESIA_E_TEATRO(5L),
    ARTE_E_CULTURA(6L),
    GASTRONOMIA(7L),
    ESPORTES_E_LAZER(8L),
    GRAPHIC_NOVELS_E_QUADRINHOS(9L),
    ENSAIOS(10L),
    GUIAS_E_MANUAIS(11L),
    ESOTERISMO_E_ESPIRITUALIDADE(12L),
    AUTOAJUDA_ESPIRITUAL(13L),
    DIDATICOS_E_PARADIDATICOS(14L),
    LIVROS_DE_REFERENCIA(15L),
    EROTISMO(16L),
    LIVROS_INTERATIVOS(17L),
    NOVELAS_LITERARIAS(18L);

    private final Long codigo;

    public static Categorias fromCodigo(Long codigo) {

        for (Categorias categoria : Categorias.values()) {

            if (categoria.getCodigo().equals(codigo)) {

                return categoria;
            }
        }

        throw new IllegalArgumentException("Código da categoria inválido: " + codigo);
    }
}
