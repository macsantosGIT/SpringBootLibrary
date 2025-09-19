package io.github.cursospring.libraryapi.controller.dto;

import io.github.cursospring.libraryapi.model.Autor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "Autor", description = "Representa um autor")
public record AutorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatório!")
        @Size(min = 2, max = 100, message = "campo fora do tamanho padrao!")
        @Schema(name = "nome", description = "Nome completo do autor", example = "Marcelo Silva")
        String nome,
        @NotNull(message = "campo obrigatório!")
        @Past(message = "nao pode ser uma data futura!")
        @Schema(name = "dataNascimento", description = "Data de nascimento do autor", example = "1980-05-15")
        LocalDate dataNascimento,
        @NotBlank(message = "campo obrigatório!")
        @Size(min = 2, max = 50, message = "campo fora do tamanho padrao!")
        @Schema(name = "nacionalidade", description = "Nacionalidade do autor", example = "Brasileira")
        String nacionalidade) {
}
