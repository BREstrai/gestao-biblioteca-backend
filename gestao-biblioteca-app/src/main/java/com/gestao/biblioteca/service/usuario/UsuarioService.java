package com.gestao.biblioteca.service.usuario;

import com.brestrai.utils.commons.domain.response.ErrorResponse;
import com.brestrai.utils.commons.service.AbstractService;
import com.gestao.biblioteca.domain.usuario.Usuario;
import com.gestao.biblioteca.domain.usuario.UsuarioDto;
import com.gestao.biblioteca.exceptions.UsuarioException;
import com.gestao.biblioteca.repository.usuario.UsuarioRepository;
import com.gestao.biblioteca.service.emprestimo.CheckRelacionamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
public class UsuarioService extends AbstractService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CheckRelacionamentoService checkRelacionamentoService;

    public List<Usuario> buscarTodosUsuarios() {

        return usuarioRepository.findAll();
    }

    public Usuario criarUsuario(UsuarioDto usuarioDto) {

        Usuario model = usuarioDto.toModel();

        validarTentativaDuplicarUsuario(model);

        return usuarioRepository.save(model);
    }

    public Usuario atualizar(Long idUsuario, UsuarioDto usuarioDto) {

        Usuario usuario = buscarUsuarioPorId(idUsuario);

        usuario.setNome(usuarioDto.nome());
        usuario.setTelefone(usuarioDto.telefone());

        return usuarioRepository.save(usuario);
    }

    public Usuario buscarUsuarioPorId(Long idUsuario) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);

        if (usuarioOptional.isPresent()) {

            Usuario usuario = usuarioOptional.get();

            return usuario;
        }

        String msg = String.format("Não foi possível localizar o usuário pelo ID %s", idUsuario);

        throw new UsuarioException(msg, new ErrorResponse(HttpStatus.NOT_FOUND, msg));
    }

    public void deletarUsuario(Long idUsuario) {

        checkRelacionamentoService.permiteDeletarUsuario(idUsuario);

        Usuario usuario = buscarUsuarioPorId(idUsuario);

        usuarioRepository.delete(usuario);
    }

    private void validarTentativaDuplicarUsuario(Usuario model) {

        Usuario usuario = usuarioRepository.findUsuarioByEmail(model.getEmail());

        if (nonNull(usuario)) {

            String msg = String.format("Usuário com e-mail %s já cadastrado.", usuario.getEmail());

            throw new UsuarioException(msg, new ErrorResponse(HttpStatus.CONFLICT, msg));
        }
    }
}
