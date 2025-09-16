package io.github.cursospring.libraryapi.controller;

import io.github.cursospring.libraryapi.model.Client;
import io.github.cursospring.libraryapi.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
public class ClientController {

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
        var senhaCriptografada = encoder.encode((client.getClientSecret()));
        client.setClientSecret(senhaCriptografada);
        service.salvar(client);
    }
}
