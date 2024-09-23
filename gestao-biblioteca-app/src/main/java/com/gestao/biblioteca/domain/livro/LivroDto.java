package com.gestao.biblioteca.domain.livro;

import com.brestrai.utils.commons.domain.IDto;
import com.gestao.biblioteca.domain.enumerations.Categorias;
import com.gestao.biblioteca.domain.enumerations.StatusEmprestimo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record LivroDto(
        Long idLivro,
        @NotBlank(message = "Titulo do livro é obrigatório")
        String titulo,

        @NotBlank(message = "Autor do livro é obrigatório")
        String autor,

        @NotBlank(message = "Código ISBN do livro é obrigatório")
        String isbn,

        @NotNull(message = "Categoria do livro é obrigatório")
        Categorias categoria,

        @NotNull(message = "Categoria do livro é obrigatório")
        LocalDateTime dhPublicacao,

        StatusEmprestimo StatusEmprestimo
) implements IDto<Livro> {

    @Override
    public Livro toModel() {

        return Livro.builder()
                .id(this.idLivro)
                .titulo(this.titulo)
                .autor(this.autor)
                .categoria(this.categoria.getCodigo())
                .isbn(this.isbn)
                .dhPublicacao(this.dhPublicacao)
                .build();
    }
}
