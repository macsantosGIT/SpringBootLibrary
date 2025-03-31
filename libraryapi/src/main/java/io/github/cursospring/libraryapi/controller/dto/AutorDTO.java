package io.github.cursospring.libraryapi.controller.dto;

import io.github.cursospring.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(
        UUID id,
        @NotBlank(message = "campo obrigatório!")
        @Size(min = 2, max = 100, message = "campo fora do tamanho padrao!")
        String nome,
        @NotNull(message = "campo obrigatório!")
        @Past(message = "nao pode ser uma data futura!")
        LocalDate dataNascimento,
        @NotBlank(message = "campo obrigatório!")
        @Size(min = 2, max = 50, message = "campo fora do tamanho padrao!")
        String nacionalidade) {
}
