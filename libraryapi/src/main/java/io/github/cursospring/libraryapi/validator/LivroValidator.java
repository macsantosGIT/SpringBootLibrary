package io.github.cursospring.libraryapi.validator;

import io.github.cursospring.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.cursospring.libraryapi.model.Livro;
import io.github.cursospring.libraryapi.repository.LivroRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class LivroValidator {

    private LivroRepository repository;

    public LivroValidator(LivroRepository repository) {
        this.repository = repository;
    }

    public void validar(Livro livro){
        if(existeLivroCadastrado(livro)){
            throw  new RegistroDuplicadoException("Livro já cadastrado!");
        }
    }

    private boolean existeLivroCadastrado(Livro livro){
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn()).stream()
                .filter(l -> Objects.equals(l.getIsbn(), livro.getIsbn()))
                .findFirst();

        if(livro.getId() == null){
            return livroEncontrado.isPresent();
        }
        return !livro.getIsbn().equals(livroEncontrado.get().getIsbn()) && livroEncontrado.isPresent();
    }
}
