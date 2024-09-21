package com.gestao.biblioteca.service.emprestimo;

import com.gestao.biblioteca.domain.emprestimo.Emprestimo;
import com.gestao.biblioteca.domain.emprestimo.EmprestimoDto;
import com.gestao.biblioteca.domain.enumerations.Categorias;
import com.gestao.biblioteca.domain.enumerations.StatusEmprestimo;
import com.gestao.biblioteca.domain.livro.Livro;
import com.gestao.biblioteca.domain.livro.LivroDto;
import com.gestao.biblioteca.domain.usuario.Usuario;
import com.gestao.biblioteca.domain.usuario.UsuarioDto;
import com.gestao.biblioteca.exceptions.DevolucaoException;
import com.gestao.biblioteca.exceptions.LivroException;
import com.gestao.biblioteca.exceptions.UsuarioException;
import com.gestao.biblioteca.repository.emprestimo.EmprestimoRepository;
import com.gestao.biblioteca.service.livro.LivroService;
import com.gestao.biblioteca.service.usuario.UsuarioService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.gestao.biblioteca.domain.enumerations.StatusEmprestimo.DISPONIVEL;
import static com.gestao.biblioteca.domain.enumerations.StatusEmprestimo.EMPRESTADO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmprestimoServiceTest {

    @InjectMocks
    private EmprestimoService emprestimoService;

    @Mock
    private EmprestimoRepository emprestimoRepository;

    @Mock
    private LivroService livroService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private Emprestimo emprestimo;

    @Mock
    private Livro livro;

    @Mock
    private Usuario usuario;

    @Mock
    private StatusEmprestimo statusEmprestimo;

    private AutoCloseable autoCloseable;

    LivroDto livroDto = new LivroDto(2L, "Clean Code", "Lima", "1234567890", Categorias.FICCAO, LocalDateTime.now());
    UsuarioDto usuarioDto = new UsuarioDto(1L, "Bruno Estrai", "brunoestrai@gmail.com", "123456789", LocalDateTime.now());
    EmprestimoDto emprestimoDto = new EmprestimoDto(1L, usuarioDto, livroDto, StatusEmprestimo.DISPONIVEL, null, null);

    @Before
    public void init() {

        autoCloseable = MockitoAnnotations.openMocks(this);

        when(emprestimo.getId()).thenReturn(1L);

        when(emprestimo.getLivro()).thenReturn(livro);
    }

    @After
    public void finish() throws Exception {

        autoCloseable.close();
    }

    @Test
    public void testa_buscar_todos_emprestimos() {

        when(emprestimoRepository.findAll()).thenReturn(Collections.nCopies(5, emprestimo));

        List<Emprestimo> emprestimos = emprestimoService.buscarTodosEmprestimos();

        Assert.assertEquals(5, emprestimos.size());
    }

    @Test(expected = UsuarioException.class)
    public void testa_buscar_sugestao_emprestimos_para_usuario_invalido_retorna_exception() {

        when(usuarioService.buscarUsuarioPorId(any())).thenThrow(UsuarioException.class);

        emprestimoService.buscarSugestaoEmprestimos(1L);

        verify(livroService).buscarLivrosNaoLidos(any());
    }

    @Test
    public void testa_buscar_sugestao_emprestimos_para_usuario() {

        emprestimoService.buscarSugestaoEmprestimos(1L);

        verify(livroService).buscarLivrosNaoLidos(any());
    }

    @Test
    public void testa_emprestar_livro_com_sucesso() {

        Emprestimo model = emprestimoDto.toModel();

        when(usuarioService.buscarUsuarioPorId(1L)).thenReturn(usuario);

        when(emprestimoRepository.save(any())).thenReturn(model);

        emprestimoService.emprestarLivro(emprestimoDto);

        verify(emprestimoRepository).save(any());
    }

    @Test(expected = DevolucaoException.class)
    public void testa_emprestar_livro_indisponivel_retornando_exception() {

        when(emprestimoRepository.findByLivroIdAndStatus(any(), any())).thenReturn(emprestimo);

        emprestimoService.emprestarLivro(emprestimoDto);
    }

    @Test(expected = DevolucaoException.class)
    public void testa_emprestar_livro_para_usuario_invalido_retorna_exception() {

        when(usuarioService.buscarUsuarioPorId(1L)).thenThrow(UsuarioException.class);

        emprestimoService.emprestarLivro(emprestimoDto);
    }

    @Test(expected = DevolucaoException.class)
    public void testa_emprestar_livro_invalido_retorna_exception() {

        when(livroService.buscarLivroPorId(any())).thenThrow(LivroException.class);

        emprestimoService.emprestarLivro(emprestimoDto);
    }

    @Test
    public void testa_devolver_livro_com_sucesso() {

        when(emprestimoRepository.findById(1L)).thenReturn(Optional.of(emprestimo));

        when(emprestimoRepository.save(any())).thenReturn(emprestimo);

        when(emprestimo.getStatus()).thenReturn(EMPRESTADO.getCodigo());

        Emprestimo retorno = emprestimoService.devolverLivro(emprestimo.getId());

        Assert.assertNotNull(retorno);

        verify(emprestimoRepository).save(any());
    }

    @Test(expected = DevolucaoException.class)
    public void testa_devolver_livro_disponivel_retornando_exception() {

        when(emprestimoRepository.findById(1L)).thenReturn(Optional.of(emprestimo));

        when(emprestimo.getStatus()).thenReturn(DISPONIVEL.getCodigo());

        emprestimoService.devolverLivro(1L);
    }

    @Test
    public void testa_buscar_emprestimo_por_id_com_sucesso() {

        when(emprestimoRepository.findById(emprestimo.getId())).thenReturn(Optional.of(emprestimo));

        Emprestimo retorno = emprestimoService.buscarEmprestimoPorId(emprestimo.getId());

        Assert.assertNotNull(retorno);

        Assert.assertEquals(1L, retorno.getId().longValue());
    }

    @Test(expected = DevolucaoException.class)
    public void testa_buscar_emprestimo_por_id_inexistente() {

        when(emprestimoRepository.findById(emprestimo.getId())).thenReturn(Optional.empty());

        emprestimoService.buscarEmprestimoPorId(1L);
    }
}
