package com.gestao.biblioteca.repository.livro;

import com.gestao.biblioteca.domain.livro.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Livro findLivroByIsbn(String isbn);

    List<Livro> findByCategoriaIn(List<Long> listaCategorias);
}
