package com.gestao.biblioteca.domain.usuario;

import com.brestrai.utils.commons.domain.IModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "USUARIOS")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements IModel<UsuarioDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    @Column(name = "DATA_CADASTRO", insertable = false)
    private LocalDateTime dhCadastro;

    @Override
    public UsuarioDto toDto() {

        return new UsuarioDto(
                this.id,
                this.nome,
                this.email,
                this.telefone,
                this.dhCadastro
        );
    }

    @Override
    public String toString() {

        return "Usuario{" +
                "idLivro=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", dhCadastro=" + dhCadastro +
                '}';
    }
}
