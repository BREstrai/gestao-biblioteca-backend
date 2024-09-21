package com.gestao.biblioteca.controller.emprestimo;

import com.gestao.biblioteca.domain.emprestimo.Emprestimo;
import com.gestao.biblioteca.domain.emprestimo.EmprestimoDto;
import com.gestao.biblioteca.domain.livro.Livro;
import com.gestao.biblioteca.domain.livro.LivroDto;
import com.gestao.biblioteca.service.emprestimo.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("emprestimo")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping("/{idEmprestimo}")
    public EmprestimoDto buscarEmprestimoPorId(@PathVariable Long idEmprestimo) {

        Emprestimo emprestimo = emprestimoService.buscarEmprestimoPorId(idEmprestimo);

        return emprestimo.toDto();
    }

    @GetMapping("/sugestao")
    public List<LivroDto> sugerirEmprestimos(@RequestParam Long idUsuario) {

        List<Livro> listaLivros = emprestimoService.buscarSugestaoEmprestimos(idUsuario);

        List<LivroDto> listaLivrosDto = listaLivros.stream()
                .map(Livro::toDto)
                .toList();

        return listaLivrosDto;
    }

    @GetMapping
    public List<EmprestimoDto> buscarTodosEmprestimos() {

        List<Emprestimo> emprestimos = emprestimoService.buscarTodosEmprestimos();

        List<EmprestimoDto> emprestimoDto = emprestimos.stream()
                .map(Emprestimo::toDto)
                .collect(Collectors.toList());

        return emprestimoDto;
    }

    @PostMapping
    public ResponseEntity<EmprestimoDto> emprestarLivro(@RequestBody @Valid EmprestimoDto emprestimoDto) {

        Emprestimo emprestimo = emprestimoService.emprestarLivro(emprestimoDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimo.toDto());
    }

    @PutMapping
    public ResponseEntity<EmprestimoDto> devolver(@RequestParam Long idEmprestimo) {

        Emprestimo emprestimo = emprestimoService.devolverLivro(idEmprestimo);

        return ResponseEntity.ok(emprestimo.toDto());
    }
}
