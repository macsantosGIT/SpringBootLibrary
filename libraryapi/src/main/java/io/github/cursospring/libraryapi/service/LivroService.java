package io.github.cursospring.libraryapi.service;

import io.github.cursospring.libraryapi.model.GeneroLivro;
import io.github.cursospring.libraryapi.model.Livro;
import io.github.cursospring.libraryapi.repository.LivroRepository;
import io.github.cursospring.libraryapi.repository.specs.LivroSpecs;
import io.github.cursospring.libraryapi.validator.LivroValidator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.cursospring.libraryapi.repository.specs.LivroSpecs.*;

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

    public List<Livro> pesquisa(
            String isbn, String titulo, String nomeAutor,
            GeneroLivro genero, Integer anoPublicacao){
        // where 0=0 - retorno uma clausula verdadeira
        Specification<Livro> specs =
                Specification.where(((root, query,
                                      cb) -> cb.conjunction()));
        if(isbn != null)
            specs = specs.and(isbnEqual(isbn));
        if(titulo != null)
            specs = specs.and(tituloLike(titulo));
        if(genero != null)
            specs = specs.and(generoEqual(genero));
        if(anoPublicacao != null)
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        if(nomeAutor != null)
            specs = specs.and(nomeAutorLike(nomeAutor));

        return repository.findAll(specs);
    }


    public void atualizar(Livro livro) {
        if(livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessaŕio que o livro esteja cadastrado");
        }

        repository.save(livro);
    }
}
