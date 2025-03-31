package io.github.cursospring.libraryapi.service;

import io.github.cursospring.libraryapi.model.Livro;
import io.github.cursospring.libraryapi.repository.LivroRepository;
import io.github.cursospring.libraryapi.validator.LivroValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LivroService {

    private final LivroRepository repository;
    private final LivroValidator validator;

    public LivroService(LivroRepository repository, LivroValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Livro livro) {
        repository.delete(livro);
    }


}
