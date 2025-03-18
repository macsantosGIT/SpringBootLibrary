package io.github.cursospring.libraryapi.service;

import io.github.cursospring.libraryapi.exceptions.OperacaoNaoPermitidaException;
import io.github.cursospring.libraryapi.model.Autor;
import io.github.cursospring.libraryapi.repository.AutorRepository;
import io.github.cursospring.libraryapi.repository.LivroRepository;
import io.github.cursospring.libraryapi.validator.AutorValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {
    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;

    public AutorService(AutorRepository repository, AutorValidator validator, LivroRepository livroRepository){
        this.repository = repository;
        this.validator = validator;
        this.livroRepository = livroRepository;
    }

    public Autor salvar(Autor autor){
        validator.validar(autor);
        return repository.save(autor);
    }

    public Autor atualizar(Autor autor){
        if(autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessaŕio que o autor esteja cadastrado");
        }
        validator.validar(autor);
        return repository.save(autor);
    }

    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        if(possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Autor possuí livros cadastrados!");
        }
        repository.delete(autor);
    }

    public List<Autor> pesquisar(String nome, String nacionalidade){
        System.out.println("Nome :" + nome);
        System.out.println("Nacionalidade :" + nacionalidade);

        if(nome != null && nacionalidade != null){
           return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }
        if(nome != null){
           return repository.findByNome(nome);
        }
        if(nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }
        return repository.findAll();
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
