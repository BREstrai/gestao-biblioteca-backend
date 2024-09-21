package com.gestao.biblioteca.domain.emprestimo;

import com.brestrai.utils.commons.domain.IDto;
import com.gestao.biblioteca.domain.enumerations.StatusEmprestimo;
import com.gestao.biblioteca.domain.livro.LivroDto;
import com.gestao.biblioteca.domain.usuario.UsuarioDto;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record EmprestimoDto(
        Long id,

        @NotNull(message = "Usuário do empréstimo é obrigatório")
        UsuarioDto usuario,

        @NotNull(message = "Livro emprestado é obrigatório")
        LivroDto livro,

        StatusEmprestimo statusEmprestimo,

        LocalDateTime dhEmprestimo,

        LocalDateTime dhDevolucao
) implements IDto<Emprestimo> {

    @Override
    public Emprestimo toModel() {

        return Emprestimo.builder()
                .id(this.id)
                .livro(this.livro.toModel())
                .usuario(this.usuario.toModel())
                .build();
    }
}
