package com.gestao.biblioteca.service.emprestimo;

import com.gestao.biblioteca.domain.emprestimo.Emprestimo;
import com.gestao.biblioteca.exceptions.LivroException;
import com.gestao.biblioteca.exceptions.UsuarioException;
import com.gestao.biblioteca.repository.emprestimo.EmprestimoRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheckRelacionamentoServiceTest {

    @InjectMocks
    private CheckRelacionamentoService checkRelacionamentoService;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private Emprestimo emprestimo;

    private AutoCloseable autoCloseable;

    @Before
    public void init() {

        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void finish() throws Exception {

        autoCloseable.close();
    }

    @Test(expected = UsuarioException.class)
    public void testa_se_existe_movimentos_para_usuario_impede_exclusao_retornando_exception() {

        when(emprestimoRepository.findByUsuarioId(any())).thenReturn(Collections.singletonList(emprestimo));

        checkRelacionamentoService.permiteDeletarUsuario(1L);
    }

    @Test(expected = LivroException.class)
    public void testa_se_existe_movimentos_para_livro_impede_exclusao_retornando_exception() {

        when(emprestimoRepository.findByLivroId(any())).thenReturn(Collections.singletonList(emprestimo));

        checkRelacionamentoService.permiteDeletarLivro(1L);
    }

    @Test
    public void testa_se_existe_movimentos_para_usuario_senao_permite_exclusao() {

        when(emprestimoRepository.findByUsuarioId(any())).thenReturn(Collections.emptyList());

        checkRelacionamentoService.permiteDeletarUsuario(1L);
    }

    @Test
    public void testa_se_existe_movimentos_para_livro_senao_permite_exclusao() {

        when(emprestimoRepository.findByLivroId(any())).thenReturn(Collections.emptyList());

        checkRelacionamentoService.permiteDeletarLivro(1L);
    }

}