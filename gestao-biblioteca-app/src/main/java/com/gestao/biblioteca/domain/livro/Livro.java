package com.gestao.biblioteca.domain.livro;

import com.brestrai.utils.commons.domain.IModel;
import com.gestao.biblioteca.domain.enumerations.Categorias;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "LIVROS")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Livro implements IModel<LivroDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String autor;

    private String isbn;

    private Long categoria;

    @Column(name = "DATA_PUBLICACAO")
    private LocalDateTime dhPublicacao;

    @Override
    public LivroDto toDto() {

        return new LivroDto(
                this.id,
                this.titulo,
                this.autor,
                this.isbn,
                Categorias.fromCodigo(this.categoria),
                this.dhPublicacao
        );
    }

    @Override
    public String toString() {

        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", categoria=" + categoria +
                ", dhPublicacao=" + dhPublicacao +
                '}';
    }
}
