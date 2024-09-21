package com.gestao.biblioteca.controller.livro;

import com.gestao.biblioteca.domain.livro.Livro;
import com.gestao.biblioteca.domain.livro.LivroDto;
import com.gestao.biblioteca.service.livro.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping("/{idLivro}")
    public LivroDto buscarLivroPorId(@PathVariable Long idLivro) {

        Livro livro = livroService.buscarLivroPorId(idLivro);

        return livro.toDto();
    }

    @GetMapping
    public List<LivroDto> buscarTodosLivros() {

        List<Livro> livros = livroService.buscarTodosLivros();

        List<LivroDto> livroDto = livros.stream()
                .map(Livro::toDto)
                .collect(Collectors.toList());

        return livroDto;
    }

    @PostMapping
    public ResponseEntity<LivroDto> criarLivro(@RequestBody @Valid LivroDto livroDto) {

        Livro livro = livroService.criarLivro(livroDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(livro.toDto());
    }

    @PutMapping
    public ResponseEntity<LivroDto> atualizar(@RequestParam Long idLivro,
                                              @RequestBody @Valid LivroDto livroDto) {

        Livro livro = livroService.atualizar(idLivro, livroDto);

        return ResponseEntity.ok(livro.toDto());
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarLivro(@RequestParam Long idLivro) {

        livroService.deletarLivro(idLivro);

        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
