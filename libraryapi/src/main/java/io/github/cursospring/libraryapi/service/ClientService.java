package io.github.cursospring.libraryapi.service;

import io.github.cursospring.libraryapi.model.Client;
import io.github.cursospring.libraryapi.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client salvar(Client client){
        return clientRepository.save(client);
    }

    public Client obterPorClienteID(String id) {
        return clientRepository.findByClientId(id);

    }
}
