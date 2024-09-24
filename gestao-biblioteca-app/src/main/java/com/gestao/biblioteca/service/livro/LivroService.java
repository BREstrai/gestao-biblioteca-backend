package com.gestao.biblioteca.service.livro;

import com.brestrai.utils.commons.domain.response.ErrorResponse;
import com.brestrai.utils.commons.service.AbstractService;
import com.gestao.biblioteca.domain.emprestimo.Emprestimo;
import com.gestao.biblioteca.domain.enumerations.StatusEmprestimo;
import com.gestao.biblioteca.domain.livro.Livro;
import com.gestao.biblioteca.domain.livro.LivroDto;
import com.gestao.biblioteca.exceptions.LivroException;
import com.gestao.biblioteca.repository.livro.LivroRepository;
import com.gestao.biblioteca.service.emprestimo.CheckRelacionamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
public class LivroService extends AbstractService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CheckRelacionamentoService checkRelacionamentoService;

    public List<Livro> buscarTodosLivros() {

        List<Livro> todosLivros = livroRepository.findAll();

        todosLivros.forEach(livro -> {

            atribuirStatus(livro);
        });

        return todosLivros;
    }

    private void atribuirStatus(Livro livro) {

        StatusEmprestimo statusEmprestimo = checkRelacionamentoService.consultarStatusLivro(livro);

        livro.setStatusEmprestimo(statusEmprestimo);
    }

    public Livro criarLivro(LivroDto livroDto) {

        Livro model = livroDto.toModel();

        validarTentativaDuplicarLivro(model);

        validarDataPublicacao(model);

        return livroRepository.save(model);
    }

    public Livro atualizar(Long idLivro, LivroDto livroDto) {

        Livro livro = buscarLivroPorId(idLivro);

        validarDataPublicacao(livro);

        livro.setTitulo(livroDto.titulo());
        livro.setAutor(livroDto.autor());
        livro.setCategoria(livroDto.categoria().getCodigo());
        livro.setDhPublicacao(livroDto.dhPublicacao());

        return livroRepository.save(livro);
    }

    public Livro buscarLivroPorId(Long idLivro) {

        Optional<Livro> livroOptional = livroRepository.findById(idLivro);

        if (livroOptional.isPresent()) {

            Livro livro = livroOptional.get();

            atribuirStatus(livro);

            return livro;
        }

        String msg = String.format("Não foi possível localizar o livro pelo ID %s", idLivro);

        throw new LivroException(msg, new ErrorResponse(HttpStatus.NOT_FOUND, msg));
    }

    public void deletarLivro(Long idLivro) {

        checkRelacionamentoService.permiteDeletarLivro(idLivro);

        Livro livro = buscarLivroPorId(idLivro);

        livroRepository.delete(livro);
    }

    private void validarTentativaDuplicarLivro(Livro model) {

        Livro livro = livroRepository.findLivroByIsbn(model.getIsbn());

        if (nonNull(livro)) {

            String msg = String.format("Livro com ISBN %s já cadastrado.", livro.getIsbn());

            throw new LivroException(msg, new ErrorResponse(HttpStatus.CONFLICT, msg));
        }
    }

    private void validarDataPublicacao(Livro model) {

        if (model.getDhPublicacao().isAfter(LocalDate.now())) {

            String msg = "Não é possível ter um livro com data de publicação a frente da data atual.";

            throw new LivroException(msg, new ErrorResponse(HttpStatus.BAD_REQUEST, msg));
        }
    }

    public List<Livro> buscarLivrosNaoLidos(List<Emprestimo> listaEmprestimos) {

        List<Livro> listaLivrosLidos = listaEmprestimos.stream()
                .map(Emprestimo::getLivro)
                .distinct()
                .toList();

        List<Long> listaCategorias = listaLivrosLidos.stream()
                .map(Livro::getCategoria)
                .distinct()
                .toList();

        List<Livro> livrosEncontrados = livroRepository.findByCategoriaIn(listaCategorias);

        livrosEncontrados.removeAll(listaLivrosLidos);

        return livrosEncontrados;
    }
}
