package io.github.cursospring.libraryapi.service;

import io.github.cursospring.libraryapi.model.Autor;
import io.github.cursospring.libraryapi.model.GeneroLivro;
import io.github.cursospring.libraryapi.model.Livro;
import io.github.cursospring.libraryapi.repository.AutorRepository;
import io.github.cursospring.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    AutorRepository autorRepository;
    @Autowired
    LivroRepository livroRepository;


    @Transactional
    public void atualizacaoSemAtualizar(){
        var livro = livroRepository
                .findById(UUID.fromString("9c8b0638-d8cb-4320-bb23-3787b01cf70c"))
                .orElse(null);

        livro.setDataPublicacao(LocalDate.of(1980,1,10));
    }

    @Transactional
    public void executar(){
        Autor autor = new Autor();
        autor.setNome("Jose");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951,1,31));
        autorRepository.saveAndFlush(autor);


        Livro livro = new Livro();
        livro.setIsbn("90887-84876");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Licro de ficcção");
        livro.setDataPublicacao(LocalDate.of(1972,2,7));
        livroRepository.saveAndFlush(livro);

        if(autor.getNome().equals("Jose")){
            throw new RuntimeException("Rollback!");
        }

    }

}
