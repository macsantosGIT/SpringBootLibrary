package io.github.cursospring.libraryapi.controller.mappers;

import io.github.cursospring.libraryapi.controller.dto.AutorDTO;
import io.github.cursospring.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
