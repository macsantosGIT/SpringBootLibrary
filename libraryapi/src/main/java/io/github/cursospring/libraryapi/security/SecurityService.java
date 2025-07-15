package io.github.cursospring.libraryapi.security;

import io.github.cursospring.libraryapi.model.Usuario;
import io.github.cursospring.libraryapi.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {

    private final UsuarioService service;

    public SecurityService(UsuarioService service) {
        this.service = service;
    }

    public Usuario obterUsuarioLogago(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof CustomAuthentication customAuth){
            return customAuth.getUsuario();
        }
        return null;
    }
}
