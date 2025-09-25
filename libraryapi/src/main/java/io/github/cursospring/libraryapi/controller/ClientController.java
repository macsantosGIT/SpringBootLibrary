package io.github.cursospring.libraryapi.controller;

import io.github.cursospring.libraryapi.model.Client;
import io.github.cursospring.libraryapi.service.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@Tag(name = "Clients", description = "Gerenciamento de clients")
@Slf4j
public class ClientController {

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);
    private final ClientService service;
    private final PasswordEncoder encoder;

    public ClientController(ClientService clientService,
                            PasswordEncoder encoder) {
        this.service = clientService;
        this.encoder = encoder;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvar(@RequestBody Client client){
        log.info("Registrando novo client: {} com scopo {}", client.getClientId(), client.getScope());
        var senhaCriptografada = encoder.encode((client.getClientSecret()));
        client.setClientSecret(senhaCriptografada);
        service.salvar(client);
    }
}
