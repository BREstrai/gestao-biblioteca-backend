package com.gestao.biblioteca.domain.usuario;

import com.brestrai.utils.commons.domain.IDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record UsuarioDto(
        Long idUsuario,
        @NotBlank(message = "Nome do usuário é obrigatório")
        String nome,
        @NotBlank(message = "Email do usuário é obrigatório")
        @Email(message = "Email deve ser válido")
        String email,

        @NotBlank(message = "Telefone do usuário é obrigatório")
        String telefone,

        LocalDateTime dhCadastro
) implements IDto<Usuario> {

    @Override
    public Usuario toModel() {

        return Usuario.builder()
                .id(this.idUsuario)
                .nome(this.nome)
                .email(this.email)
                .telefone(this.telefone)
                .build();
    }
}
