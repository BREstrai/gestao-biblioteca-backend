package com.gestao.biblioteca.repository.emprestimo;

import com.gestao.biblioteca.domain.emprestimo.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    Emprestimo findByLivroIdAndStatus(Long id, Long status);

    List<Emprestimo> findByUsuarioId(Long id);

    List<Emprestimo> findByLivroId(Long id);
}
