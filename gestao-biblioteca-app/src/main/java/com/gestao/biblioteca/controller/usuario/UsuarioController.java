package com.gestao.biblioteca.controller.usuario;

import com.gestao.biblioteca.domain.usuario.Usuario;
import com.gestao.biblioteca.domain.usuario.UsuarioDto;
import com.gestao.biblioteca.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{idUsuario}")
    public UsuarioDto buscarUsuarioPorId(@PathVariable Long idUsuario) {

        Usuario usuario = usuarioService.buscarUsuarioPorId(idUsuario);

        return usuario.toDto();
    }

    @GetMapping
    public List<UsuarioDto> buscarTodosUsuarios() {

        List<Usuario> usuarios = usuarioService.buscarTodosUsuarios();

        List<UsuarioDto> usuarioDto = usuarios.stream()
                .map(Usuario::toDto)
                .collect(Collectors.toList());

        return usuarioDto;
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> criarUsuario(@RequestBody @Valid UsuarioDto usuarioDto) {

        Usuario usuario = usuarioService.criarUsuario(usuarioDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario.toDto());
    }

    @PutMapping
    public ResponseEntity<UsuarioDto> atualizar(@RequestParam Long idUsuario,
                                                @RequestBody @Valid UsuarioDto usuarioDto) {

        Usuario usuario = usuarioService.atualizar(idUsuario, usuarioDto);

        return ResponseEntity.ok(usuario.toDto());
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarUsuario(@RequestParam Long idUsuario) {

        usuarioService.deletarUsuario(idUsuario);

        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
