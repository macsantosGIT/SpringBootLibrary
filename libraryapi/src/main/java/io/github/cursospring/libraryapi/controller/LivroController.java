package io.github.cursospring.libraryapi.controller;

import io.github.cursospring.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.cursospring.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.cursospring.libraryapi.controller.mappers.LivroMapper;
import io.github.cursospring.libraryapi.model.Livro;
import io.github.cursospring.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
// http://localhost:8080/livros
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    public LivroController(LivroService service, LivroMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);
        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(
            @PathVariable("id") String id){
            return service.obterPorId(UUID.fromString(id))
                    .map(livro -> {
                        var dto = mapper.toDTO(livro);
                        return ResponseEntity.ok(dto);
                    }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                   service.deletar(livro);
                   return ResponseEntity.noContent().build();
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}

