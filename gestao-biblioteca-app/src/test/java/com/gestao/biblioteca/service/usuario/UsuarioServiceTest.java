package com.gestao.biblioteca.service.usuario;

import com.gestao.biblioteca.domain.usuario.Usuario;
import com.gestao.biblioteca.domain.usuario.UsuarioDto;
import com.gestao.biblioteca.exceptions.UsuarioException;
import com.gestao.biblioteca.repository.usuario.UsuarioRepository;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CheckRelacionamentoService checkRelacionamentoService;

    @Mock
    private Usuario usuario;

    private final UsuarioDto usuarioDto = new UsuarioDto(1L, "Teste", "brunoestrai@gmail.com",
            "46988275899", LocalDateTime.now().plusDays(5));
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
    public void testa_se_chamada_retorna_todos_usuarios() {

        Mockito.when(usuarioRepository.findAll()).thenReturn(Collections.nCopies(5, usuario));

        List<Usuario> listaUsuarios = usuarioService.buscarTodosUsuarios();

        Mockito.verify(usuarioRepository).findAll();

        Assert.assertEquals(5, listaUsuarios.size());
    }

    @Test
    public void testa_se_chamada_retorna_usuarios_por_id() {

        Mockito.when(usuarioRepository.findById(any())).thenReturn(Optional.of(usuario));

        Usuario retorno = usuarioService.buscarUsuarioPorId(any());

        Mockito.verify(usuarioRepository).findById(any());

        Assert.assertNotNull(retorno);
    }

    @Test(expected = UsuarioException.class)
    public void testa_se_chamada_de_usuario_por_id_quando_nao_encontrar_retorna_exception() {

        Mockito.when(usuarioRepository.findById(any())).thenReturn(Optional.empty());

        usuarioService.buscarUsuarioPorId(any());
    }

    @Test(expected = UsuarioException.class)
    public void testa_se_tentativa_de_cadastrar_usuario_tenta_duplicar_email_retorna_exception() {

        Mockito.when(usuarioRepository.findUsuarioByEmail(any())).thenReturn(usuario);

        usuarioService.criarUsuario(usuarioDto);
    }

    @Test
    public void testa_se_cadastrou_novo_usuario() {

        Usuario model = usuarioDto.toModel();

        Mockito.when(usuarioRepository.findUsuarioByEmail(any())).thenReturn(null);

        Mockito.when(usuarioRepository.save(any())).thenReturn(model);

        Usuario retorno = usuarioService.criarUsuario(usuarioDto);

        Mockito.verify(usuarioRepository).save(any());

        Assert.assertEquals(model, retorno);
    }

    @Test
    public void testa_se_atualizou_usuario() {

        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.atualizar(1L, usuarioDto);

        Mockito.verify(usuarioRepository).save(usuario);
    }

    @Test(expected = UsuarioException.class)
    public void testa_se_tentou_atualizar_usuario_com_id_inexistente_retornando_exception() {

        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        usuarioService.atualizar(1L, usuarioDto);
    }

    @Test
    public void testa_se_deletou_usuario() {

        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.deletarUsuario(1L);

        Mockito.verify(usuarioRepository).delete(usuario);
    }

    @Test(expected = UsuarioException.class)
    public void testa_se_tentou_deletar_usuario_com_id_inexistente_retornando_exception() {

        Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        usuarioService.deletarUsuario(1L);
    }
}