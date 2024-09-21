package com.gestao.biblioteca.service.livro;

import com.gestao.biblioteca.domain.emprestimo.Emprestimo;
import com.gestao.biblioteca.domain.enumerations.Categorias;
import com.gestao.biblioteca.domain.livro.Livro;
import com.gestao.biblioteca.domain.livro.LivroDto;
import com.gestao.biblioteca.exceptions.LivroException;
import com.gestao.biblioteca.repository.livro.LivroRepository;
import com.gestao.biblioteca.service.emprestimo.CheckRelacionamentoService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class LivroServiceTest {

    @InjectMocks
    private LivroService livroService;

    @Mock
    private LivroRepository livroRepository;

    @Mock
    private CheckRelacionamentoService checkRelacionamentoService;

    @Mock
    private Livro livro;

    private final LivroDto livroDto = new LivroDto(1L, "Clean Code", "Bruno Estrai",
            "000000000000", Categorias.FICCAO, LocalDateTime.now());

    private AutoCloseable autoCloseable;

    @Before
    public void init() {

        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @After
    public void finish() throws Exception {

        autoCloseable.close();
    }

    @Test
    public void testa_se_chamada_retorna_todos_livros() {

        Mockito.when(livroRepository.findAll()).thenReturn(Collections.nCopies(5, livro));

        List<Livro> listaLivros = livroService.buscarTodosLivros();

        Mockito.verify(livroRepository).findAll();

        Assert.assertEquals(5, listaLivros.size());
    }

    @Test
    public void testa_se_chamada_retorna_livro_por_id() {

        Mockito.when(livroRepository.findById(any())).thenReturn(Optional.of(livro));

        Livro retorno = livroService.buscarLivroPorId(any());

        Mockito.verify(livroRepository).findById(any());

        Assert.assertNotNull(retorno);
    }

    @Test(expected = LivroException.class)
    public void testa_se_chamada_de_livro_por_id_quando_nao_encontrar_retorna_exception() {

        Mockito.when(livroRepository.findById(any())).thenReturn(Optional.empty());

        livroService.buscarLivroPorId(any());
    }

    @Test(expected = LivroException.class)
    public void testa_se_tentativa_de_cadastrar_livro_tenta_duplicar_isbn_retorna_exception() {

        Mockito.when(livroRepository.findLivroByIsbn(any())).thenReturn(livro);

        livroService.criarLivro(livroDto);
    }

    @Test
    public void testa_se_cadastrou_novo_livro() {

        Livro model = livroDto.toModel();

        Mockito.when(livroRepository.findLivroByIsbn(any())).thenReturn(null);

        Mockito.when(livroRepository.save(any())).thenReturn(model);

        Livro retorno = livroService.criarLivro(livroDto);

        Mockito.verify(livroRepository).save(any());

        Assert.assertEquals(model, retorno);
    }

    @Test
    public void testa_se_atualizou_livro() {

        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        Mockito.when(livro.getDhPublicacao()).thenReturn(LocalDateTime.now().minusDays(1));

        livroService.atualizar(1L, livroDto);

        Mockito.verify(livroRepository).save(livro);
    }

    @Test(expected = LivroException.class)
    public void testa_se_tentou_atualizar_livro_com_id_inexistente_retornando_exception() {

        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.empty());

        livroService.atualizar(1L, livroDto);
    }

    @Test(expected = LivroException.class)
    public void testa_se_tentou_atualizar_livro_com_data_publicacao_futura_retornando_exception() {

        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        Mockito.when(livro.getDhPublicacao()).thenReturn(LocalDateTime.now().plusDays(5));

        livroService.atualizar(1L, livroDto);
    }

    @Test
    public void testa_se_deletou_livro() {

        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        livroService.deletarLivro(1L);

        Mockito.verify(livroRepository).delete(livro);
    }

    @Test(expected = LivroException.class)
    public void testa_se_tentou_deletar_livro_com_id_inexistente_retornando_exception() {

        Mockito.when(livroRepository.findById(1L)).thenReturn(Optional.empty());

        livroService.deletarLivro(1L);
    }

    @Test
    public void testa_sugestao_livros_para_usuario() {

        Livro livroLido1 = new Livro();
        livroLido1.setId(1L);
        livroLido1.setIsbn("1234567890");
        livroLido1.setCategoria(1L);

        Livro livroLido2 = new Livro();
        livroLido2.setId(3L);
        livroLido2.setIsbn("0987654321");
        livroLido2.setCategoria(1L);

        List<Emprestimo> listaEmprestimos = new ArrayList<>();
        listaEmprestimos.add(Emprestimo.builder().livro(livroLido1).build());
        listaEmprestimos.add(Emprestimo.builder().livro(livroLido2).build());

        Livro livroDisponivel = new Livro();
        livroDisponivel.setId(1L);
        livroDisponivel.setIsbn("1234567890");
        livroDisponivel.setCategoria(1L);

        Livro livroNaoLido = new Livro();
        livroNaoLido.setId(4L);
        livroNaoLido.setIsbn("1111111111");
        livroNaoLido.setCategoria(2L);

        List<Livro> listaLivros = new ArrayList<>();
        listaLivros.add(livroDisponivel);
        listaLivros.add(livroNaoLido);

        Mockito.when(livroRepository.findByCategoriaIn(List.of(1L))).thenReturn(listaLivros);

        List<Livro> livrosNaoLidos = livroService.buscarLivrosNaoLidos(listaEmprestimos);

        Assert.assertEquals(1, livrosNaoLidos.size());
        Assert.assertEquals(livroNaoLido, livrosNaoLidos.get(0));
    }

}