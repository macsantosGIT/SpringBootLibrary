package io.github.cursospring.libraryapi.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.cursospring.libraryapi.model.Autor;
import io.github.cursospring.libraryapi.model.GeneroLivro;
import io.github.cursospring.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {
    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Vinicius");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1972,2,7));
        var autorSalvo = repository.save(autor);
        System.out.println("Autor salvo :" + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        var id = UUID.fromString("cc7916a4-4ddd-4158-9fcc-312c47ce2b1d");
        Optional<Autor> possivelAutor = repository.findById(id);
        if(possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();;
            System.out.println("Dados do Autor:");
            System.out.println(possivelAutor.get());
            autorEncontrado.setDataNascimento(LocalDate.of(1999,4,11));
            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> autores = repository.findAll();
        autores.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores: " + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("48b56bc2-55fa-472b-b198-2409620925a6");
        repository.deleteById(id);
    }

    @Test
    public void deleteTest(){
        var id = UUID.fromString("cc7916a4-4ddd-4158-9fcc-312c47ce2b1d");
        var autor = repository.findById(id).get();
        repository.delete(autor);
    }

    @Test
    public void salvarAutorComLivroTest(){
        Autor autor = new Autor();
        autor.setNome("Vinicius");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1999,4,11));

        Livro livro = new Livro();
        livro.setIsbn("27589-98384");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("O roubo da casa de papel");
        livro.setDataPublicacao(LocalDate.of(1999,1,2));
        livro.setAutor(autor);

        Livro livro1 = new Livro();
        livro1.setIsbn("27581398382");
        livro1.setPreco(BigDecimal.valueOf(350));
        livro1.setGenero(GeneroLivro.CIENCIA);
        livro1.setTitulo("Estudo da teroia quantica");
        livro1.setDataPublicacao(LocalDate.of(2010,10,4));
        livro1.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro1);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    //@Transactional
    void listarLivroAutorTest(){
        var id = UUID.fromString("89d6148a-66c1-4bfa-b653-a6645329fecd");
        var autor = repository.findById(id).get();
        //buscar livros do autor
        List<Livro> listaLivros = livroRepository.findByAutor(autor);
        autor.setLivros(listaLivros);

        autor.getLivros().forEach(System.out::println);
    }


}
