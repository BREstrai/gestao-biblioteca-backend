package com.gestao.biblioteca.service.emprestimo;

import com.brestrai.utils.commons.domain.response.ErrorResponse;
import com.brestrai.utils.commons.service.AbstractService;
import com.gestao.biblioteca.domain.emprestimo.Emprestimo;
import com.gestao.biblioteca.domain.emprestimo.EmprestimoDto;
import com.gestao.biblioteca.domain.enumerations.StatusEmprestimo;
import com.gestao.biblioteca.domain.livro.Livro;
import com.gestao.biblioteca.exceptions.DevolucaoException;
import com.gestao.biblioteca.exceptions.LivroException;
import com.gestao.biblioteca.exceptions.UsuarioException;
import com.gestao.biblioteca.repository.emprestimo.EmprestimoRepository;
import com.gestao.biblioteca.service.livro.LivroService;
import com.gestao.biblioteca.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
public class EmprestimoService extends AbstractService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroService livroService;

    @Autowired
    private UsuarioService usuarioService;

    public List<Emprestimo> buscarTodosEmprestimos() {

        return emprestimoRepository.findAll();
    }

    public Emprestimo emprestarLivro(EmprestimoDto emprestimoDto) {

        Emprestimo model = emprestimoDto.toModel();

        validarPartesEmprestimo(model);

        validarTentativaEmprestarLivroIndisponivel(model);

        model.setStatus(StatusEmprestimo.EMPRESTADO.getCodigo());

        return emprestimoRepository.save(model);
    }

    public Emprestimo devolverLivro(Long idEmprestimo) {

        Emprestimo emprestimo = buscarEmprestimoPorId(idEmprestimo);

        validarDevolucao(emprestimo);

        emprestimo.setStatus(StatusEmprestimo.DISPONIVEL.getCodigo());
        emprestimo.setDhDevolucao(LocalDateTime.now());

        return emprestimoRepository.save(emprestimo);
    }

    public Emprestimo buscarEmprestimoPorId(Long idEmprestimo) {

        Optional<Emprestimo> emprestimoOptional = emprestimoRepository.findById(idEmprestimo);

        if (emprestimoOptional.isPresent()) {

            Emprestimo emprestimo = emprestimoOptional.get();

            return emprestimo;
        }

        String msg = String.format("Não foi possível localizar o empréstimo pelo ID %s", idEmprestimo);

        throw new DevolucaoException(msg, new ErrorResponse(HttpStatus.NOT_FOUND, msg));
    }

    public List<Livro> buscarSugestaoEmprestimos(Long idUsuario) {

        validarUsuario(idUsuario);

        List<Emprestimo> listaEmprestimos = emprestimoRepository.findByUsuarioId(idUsuario);

        return livroService.buscarLivrosNaoLidos(listaEmprestimos);
    }

    private void validarTentativaEmprestarLivroIndisponivel(Emprestimo model) {

        Emprestimo emprestimo = emprestimoRepository.findByLivroIdAndStatus(model.getLivro().getId(),
                StatusEmprestimo.EMPRESTADO.getCodigo());

        if (nonNull(emprestimo)) {

            String msg = String.format("Livro %s já emprestado.", emprestimo.getLivro().getTitulo());

            throw new DevolucaoException(msg, new ErrorResponse(HttpStatus.CONFLICT, msg));
        }
    }

    private void validarDevolucao(Emprestimo model) {

        if (!StatusEmprestimo.isEmprestado(model.getStatus())) {

            String msg = "Não é possível devolver um livro que está disponível.";

            throw new DevolucaoException(msg, new ErrorResponse(HttpStatus.BAD_REQUEST, msg));
        }
    }

    private void validarPartesEmprestimo(Emprestimo model) {

        try {

            validarLivro(model);

            validarUsuario(model.getUsuario().getId());
        } catch (LivroException e) {

            String msg = "Não foi possíve localizar o livro informado para emprestimo. ";

            logWarn(msg + model.getLivro().toString());

            throw new DevolucaoException(msg, new ErrorResponse(HttpStatus.BAD_REQUEST, msg));
        } catch (UsuarioException e) {

            String msg = "Não foi possíve localizar o usuário informado para emprestar o livro. ";

            logWarn(msg + model.getLivro().toString());

            throw new DevolucaoException(msg, new ErrorResponse(HttpStatus.BAD_REQUEST, msg));
        }
    }

    private void validarLivro(Emprestimo model) {

        livroService.buscarLivroPorId(model.getLivro().getId());
    }

    private void validarUsuario(Long idUsuario) {

        usuarioService.buscarUsuarioPorId(idUsuario);
    }
}
