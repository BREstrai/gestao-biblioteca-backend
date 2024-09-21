package com.gestao.biblioteca.domain.emprestimo;

import com.brestrai.utils.commons.domain.IModel;
import com.gestao.biblioteca.domain.enumerations.StatusEmprestimo;
import com.gestao.biblioteca.domain.livro.Livro;
import com.gestao.biblioteca.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "EMPRESTIMOS")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Emprestimo implements IModel<EmprestimoDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "LIVRO_ID", nullable = false)
    private Livro livro;

    private Long status;

    @Column(name = "DATA_EMPRESTIMO", insertable = false)
    private LocalDateTime dhEmprestimo;

    @Column(name = "DATA_DEVOLUCAO")
    private LocalDateTime dhDevolucao;

    @Override
    public EmprestimoDto toDto() {

        return new EmprestimoDto(
                this.id,
                this.usuario.toDto(),
                this.livro.toDto(),
                StatusEmprestimo.fromCodigo(this.status),
                this.dhEmprestimo,
                this.dhDevolucao
        );
    }

    @Override
    public String toString() {

        return "Emprestimo{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", livro=" + livro +
                ", status=" + status +
                ", dhEmprestimo=" + dhEmprestimo +
                ", dhDevolucao=" + dhDevolucao +
                '}';
    }
}
