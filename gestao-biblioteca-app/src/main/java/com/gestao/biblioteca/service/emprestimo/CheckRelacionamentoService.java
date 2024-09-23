package com.gestao.biblioteca.service.emprestimo;

import com.brestrai.utils.commons.domain.response.ErrorResponse;
import com.brestrai.utils.commons.service.AbstractService;
import com.gestao.biblioteca.domain.emprestimo.Emprestimo;
import com.gestao.biblioteca.domain.enumerations.StatusEmprestimo;
import com.gestao.biblioteca.domain.livro.Livro;
import com.gestao.biblioteca.exceptions.LivroException;
import com.gestao.biblioteca.exceptions.UsuarioException;
import com.gestao.biblioteca.repository.emprestimo.EmprestimoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gestao.biblioteca.domain.enumerations.StatusEmprestimo.DISPONIVEL;
import static com.gestao.biblioteca.domain.enumerations.StatusEmprestimo.EMPRESTADO;
import static java.util.Objects.isNull;

@Service
public class CheckRelacionamentoService extends AbstractService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;


    public void permiteDeletarUsuario(Long idUsuario) {

        List<Emprestimo> emprestimos = emprestimoRepository.findByUsuarioId(idUsuario);

        if (!emprestimos.isEmpty()) {

            String msg = "Usuário já possui reservas de livros, não pode ser deletado";

            throw new UsuarioException(msg, new ErrorResponse(HttpStatus.CONFLICT, msg));
        }
    }

    public void permiteDeletarLivro(Long idLivro) {

        List<Emprestimo> emprestimos = emprestimoRepository.findByLivroId(idLivro);

        if (!emprestimos.isEmpty()) {

            String msg = "Livro já possui reservas, não pode ser deletado";

            throw new LivroException(msg, new ErrorResponse(HttpStatus.CONFLICT, msg));
        }
    }

    public StatusEmprestimo consultarStatusLivro(Livro livro) {

        Emprestimo emprestimo = emprestimoRepository.findByLivroIdAndStatus(livro.getId(), EMPRESTADO.getCodigo());

        if (isNull(emprestimo)) {

            return DISPONIVEL;
        }

        return EMPRESTADO;
    }
}
