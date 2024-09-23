package com.gestao.biblioteca.domain.livro;

import com.brestrai.utils.commons.domain.IModel;
import com.gestao.biblioteca.domain.enumerations.Categorias;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

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
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof Livro)) return false;

        Livro livro = (Livro) o;

        return Objects.equals(id, livro.id) && Objects.equals(isbn, livro.isbn) &&
                Objects.equals(categoria, livro.categoria);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, isbn, categoria);
    }

    @Override
    public String toString() {

        return "Livro{" +
                "idLivro=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", isbn='" + isbn + '\'' +
                ", categoria=" + categoria +
                ", dhPublicacao=" + dhPublicacao +
                '}';
    }
}
