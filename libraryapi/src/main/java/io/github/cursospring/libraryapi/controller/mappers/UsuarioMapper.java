package io.github.cursospring.libraryapi.controller.mappers;

import io.github.cursospring.libraryapi.controller.dto.UsuarioDTO;
import io.github.cursospring.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
