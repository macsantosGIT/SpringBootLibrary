package io.github.cursospring.libraryapi.controller;

import io.github.cursospring.libraryapi.controller.dto.AutorDTO;
import io.github.cursospring.libraryapi.controller.mappers.AutorMapper;
import io.github.cursospring.libraryapi.model.Autor;
import io.github.cursospring.libraryapi.model.Usuario;
import io.github.cursospring.libraryapi.security.SecurityService;
import io.github.cursospring.libraryapi.service.AutorService;
import io.github.cursospring.libraryapi.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
// http://localhost:8080/autores
@Tag(name = "Autores", description = "Gerenciamento de autores")
@Slf4j
public class AutorController implements GenericController {

    private static final Logger log = LoggerFactory.getLogger(AutorController.class);
    private final AutorService service;
    private final AutorMapper mapper;

    public AutorController(AutorService service, SecurityService securityService, AutorMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Salvar", description = "Cadastrar novo Autor.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor cadastrado com sucesso."),
            @ApiResponse(responseCode = "422", description = "Erro de validação."),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado.")
    })
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO dto) {
        log.info("Cadastrando no autor: {}", dto.nome());

        Autor autor = mapper.toEntity(dto);
        service.salvar(autor);
        var url = gerarHeaderLocation(autor.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('GERENTE','OPERADOR')")
    @Operation(summary = "Obter Detalhe", description = "Retorna os dado do Autor por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
    })
    public ResponseEntity<AutorDTO> obterDetalhe(@PathVariable("id") String id) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        return service
                .obterPorId(idAutor)
                .map(autor -> {
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Deletar", description = "Deleta Autor existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
            @ApiResponse(responseCode = "400", description = "Autor possui livro cadastrado.")
    })
    public ResponseEntity<Void> deletar(@PathVariable("id") String id) {
        log.info("Deletando autor de ID: {}", id);
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE','OPERADOR')")
    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de autores por parametros.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso.")
    })
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> resultado = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> lista = resultado
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('GERENTE')")
    @Operation(summary = "Atualizar", description = "Atualiza dados de Autor existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado."),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado.")
    })
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody AutorDTO dto) {
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        service.atualizar(autor);
        return ResponseEntity.noContent().build();
    }
}
